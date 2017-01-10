/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.SubjectsModel;
import Object.Answer;
import Object.Chapter;
import Object.Essay;
import Object.MultipleChoice;
import Object.Subject;
import Object.Subjects;
import Views.ImportPath;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author 8TITTIT8
 */
public class ImportController {
    
    public static void showImportPath(){
        ImportPath importPath = new ImportPath(); 
    }
    
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.toLowerCase();
    }
    
    public static void importFileQuestions(String path){
        try{
            Subjects subjects = SubjectsModel.readSubjects();
            InputStream inputStream = new FileInputStream(path);
            InputStreamReader fileI = new InputStreamReader(inputStream, "Unicode");
            try (BufferedReader br = new BufferedReader(fileI)) {
                StringBuilder str = new StringBuilder("");
                String line;
                while ((line = br.readLine()) != null) {
                    str.append(line);
                }
                
                JSONArray jsonArray = new JSONArray();
                try {
                    jsonArray = new JSONArray(str.toString());
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
                
                for(int i = 0; i < jsonArray.length(); ++i){
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    int dd = -1;
                    boolean ok;
                    for(int k = 0; k < subjects.getSubjects().size(); ++k)
                        if (removeAccent(subjects.getSubject(k).getNameSubject()).equals(removeAccent(object.get("subject").toString()))) dd = k;
                    
                    System.out.println(dd);
                    if (dd == -1){
                        Subject subject = new Subject();
                        
                        subject.setNameSubject(object.get("subject").toString());                        
                        Chapter newChapter = new Chapter();
                        newChapter.setNameChapter(object.get("chapter").toString());
                        subject.setChapter(newChapter);
                        
                        if (object.get("type").equals("tự luận")){
                            Essay newEssay = new Essay();
                            newEssay.setChapter(object.get("chapter").toString());
                            newEssay.setLevel((int) (Number) object.get("level"));
                            newEssay.setContentQuestion(object.get("content").toString());
                            newEssay.setSuggest(object.get("suggest").toString());
                            subject.setEssay(newEssay);
                        }
                        if (object.get("type").equals("trắc nghiệm")){
                            MultipleChoice newMultipleChoice = new MultipleChoice();
                            newMultipleChoice.setChapter(object.get("chapter").toString());
                            newMultipleChoice.setLevel((int) (Number) object.get("level"));
                            newMultipleChoice.setContentQuestion(object.get("content").toString());
                            JSONArray arrayAnswers = (JSONArray) object.get("answers");
                            JSONObject objectAnswer;
                            for(int j = 0; j < arrayAnswers.length(); ++j){
                                Answer answer = new Answer();
                                objectAnswer = (JSONObject) arrayAnswers.get(j);
                                answer.setContentAnswer(objectAnswer.get("contentAnswer").toString());
                                if (objectAnswer.get("accuracy").toString().toLowerCase().equals("true")) answer.setAccuracy(true);
                                else answer.setAccuracy(false);
                                newMultipleChoice.setAnswer(answer);
                            }
                            subject.setMultipleChoice(newMultipleChoice);
                        }
                        subjects.setSubject(subject);
                    }
                    else{
                        //kiểm tra chapter có chưa?
                        ok = false;
                        for(int j = 0; j < subjects.getSubject(dd).getChapters().size(); ++j)
                            if (subjects.getSubject(dd).getChapter(j).getNameChapter().equals(object.get("chapter").toString())) ok = true;
                        if (ok == false){
                            Chapter newChapter = new Chapter();
                            newChapter.setNameChapter(object.get("chapter").toString());
                            subjects.getSubject(dd).setChapter(newChapter);
                        }
                        // thêm câu hỏi vào danh sách
                        if (removeAccent(object.get("type").toString()).equals("tu luan")){
                            Essay newEssay = new Essay();
                            newEssay.setChapter(object.get("chapter").toString());
                            newEssay.setLevel((int) (Number) object.get("level"));
                            newEssay.setContentQuestion(object.get("content").toString());
                            newEssay.setSuggest(object.get("suggest").toString());
                            subjects.getSubject(dd).setEssay(newEssay);
                        }
                        if (removeAccent(object.get("type").toString()).equals("trac nghiem")){
                            MultipleChoice newMultipleChoice = new MultipleChoice();
                            newMultipleChoice.setChapter(object.get("chapter").toString());
                            newMultipleChoice.setLevel((int) (Number) object.get("level"));
                            newMultipleChoice.setContentQuestion(object.get("content").toString());
                            JSONArray arrayAnswers = (JSONArray) object.get("answers");
                            JSONObject objectAnswer;
                            for(int j = 0; j < arrayAnswers.length(); ++j){
                                Answer answer = new Answer();
                                objectAnswer = (JSONObject) arrayAnswers.get(j);
                                answer.setContentAnswer(objectAnswer.get("contentAnswer").toString());
                                if (objectAnswer.get("accuracy").toString().toLowerCase().equals("true")) answer.setAccuracy(true);
                                else answer.setAccuracy(false);
                                newMultipleChoice.setAnswer(answer);
                            }
                            subjects.getSubject(dd).setMultipleChoice(newMultipleChoice);
                        }
                    }
                }
                br.close();
            }
            SubjectsModel.writeSubjects(subjects);
        } catch (FileNotFoundException exp) {
            exp.printStackTrace();
        } catch (IOException exp) {
            exp.printStackTrace();
        } catch (JSONException exp){
            exp.printStackTrace();
        }
    }
}
