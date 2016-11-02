package org.trainingTracker.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import org.trainingTracker.database.dataAccesObject.ExercisesDAO;
import org.trainingTracker.database.dataAccesObject.RecordsDAO;
import org.trainingTracker.database.valueObject.RecordVO;
import org.trainingTracker.database.valueObject.ExerciseVO;

/**
 * Servlet implementation class ListPerformed
 */
@WebServlet("/listPerformed")
public class ListPerformed extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListPerformed() {
        super();
    }

    @Override
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Reads User header and substracts user
        String name = request.getHeader("user");
        
        try {
            // Search for predifined exercises in BD
            JSONArray jsonExercises = new JSONArray();
            JSONObject exercise, record;
            
            response.setStatus(HttpServletResponse.SC_OK);
            for (ExerciseVO vo : ExercisesDAO.listExercises(name)) {
                exercise = JSONObject.fromObject(vo.serialize());
                exercise.remove("predefines");
                record = JSONObject.fromObject(RecordsDAO.listRecords(name, vo.getId(), 1).get(0).serialize());
                record.remove("exercise");
                record.remove("nick");
                record.remove("date");
                exercise.putAll(record);
                jsonExercises.add(exercise);
            }
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(jsonExercises.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error interno en el servidor. Vuelva intentarlo más tarde");
        }
    }
    
}