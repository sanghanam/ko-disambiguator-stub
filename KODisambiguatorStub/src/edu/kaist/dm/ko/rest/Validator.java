package edu.kaist.dm.ko.rest;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Validator {

	public ArrayList<ErrorType> etIn = new ArrayList<ErrorType>();
	public ArrayList<ErrorType> etOut = new ArrayList<ErrorType>();

	public boolean isValidDisambigInJSON(String disambigInJSONStr) {

		JSONParser jsonParser = new JSONParser();

		JSONObject disambigInJSON = null;

		boolean validQuestion = false;
		boolean validQuery = false;
		boolean validSlots = false;
		boolean validScore = false;

		try {

			disambigInJSON = (JSONObject) jsonParser.parse(disambigInJSONStr);

		} catch (ParseException pe) {

			etIn.add(new ErrorType(false, "parse"));

			// System.err.println(pe.toString());
			// pe.printStackTrace();

			return false;

		}

		boolean validDisambigInJSON = false;

		try {

			validDisambigInJSON = !disambigInJSON.isEmpty();
			validDisambigInJSON = (disambigInJSON.size() != 0);
		} catch (NullPointerException npe) {

			// System.err.println(npe.toString());
			// npe.printStackTrace();

			return false;

		}

		try {
			validDisambigInJSON = !disambigInJSON.get("question").equals("");
			validQuestion = true;
		} catch (NullPointerException npeQ) {
			etIn.add(new ErrorType(validDisambigInJSON, "question"));
		}
		try {
			validDisambigInJSON = !disambigInJSON.get("query").equals("");
			validQuery = true;
		} catch (NullPointerException npeQY) {
			etIn.add(new ErrorType(validDisambigInJSON, "query"));
		}
		try {
			validDisambigInJSON = !disambigInJSON.get("slots").equals(null);
			validSlots = true;
		} catch (NullPointerException npeSL) {
			etIn.add(new ErrorType(validDisambigInJSON, "slots"));
		}
		try {
			validDisambigInJSON = !disambigInJSON.get("score").equals("");
			validScore = true;
		} catch (NullPointerException npeSC) {
			etIn.add(new ErrorType(validDisambigInJSON, "score"));
		}

		if (validQuestion && validQuery && validSlots && validScore) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isValidDisambigOutJSON(String disambigOutJSONStr) {

		JSONParser jsonParser = new JSONParser();

		JSONObject disambigOutJSON = null;

		JSONObject nedJSONObject = null;

		boolean validQuestion = false;
		boolean validNed = false;
		boolean validClasses = false;
		boolean validScore = false;
		boolean validProperties = false;
		boolean validEntities = false;

		try {

			disambigOutJSON = (JSONObject) jsonParser.parse(disambigOutJSONStr);

		} catch (ParseException pe) {

			etOut.add(new ErrorType(false, "parse"));

			// System.err.println(pe.toString());
			// pe.printStackTrace();

			return false;

		}

		boolean validDisambigOutJSON = false;

		try {

			validDisambigOutJSON = !disambigOutJSON.isEmpty();
			validDisambigOutJSON = (disambigOutJSON.size() == 4);
		} catch (NullPointerException npe) {

			// System.err.println(npe.toString());
			// npe.printStackTrace();

			return false;

		}

		try {
			validDisambigOutJSON = !disambigOutJSON.get("question").equals("");
			validQuestion = true;
		} catch (NullPointerException npeQ) {
			etOut.add(new ErrorType(validDisambigOutJSON, "question"));
		}

		try {
			validDisambigOutJSON = !disambigOutJSON.get("ned").equals(null);
			JSONArray nedJSONArray = (JSONArray) disambigOutJSON.get("ned");
			nedJSONObject = (JSONObject) nedJSONArray.get(0);
			validNed = true;
		} catch (NullPointerException npeN) {
			etOut.add(new ErrorType(validDisambigOutJSON, "ned"));
			return false;
		}
		try {
			validDisambigOutJSON = !nedJSONObject.get("classes").equals(null);
			validClasses = true;
		} catch (NullPointerException npeC) {
			etOut.add(new ErrorType(validDisambigOutJSON, "classes"));
		}
		try {
			validDisambigOutJSON = !nedJSONObject.get("score").equals("");
			validScore = true;
		} catch (NullPointerException npeSC) {
			etOut.add(new ErrorType(validDisambigOutJSON, "score"));
		}
		try {
			validDisambigOutJSON = !nedJSONObject.get("properties").equals(null);
			validProperties = true;
		} catch (NullPointerException npeP) {
			etOut.add(new ErrorType(validDisambigOutJSON, "properties"));
		}
		try {
			validDisambigOutJSON = !nedJSONObject.get("entities").equals(null);
			validEntities = true;
		} catch (NullPointerException npeE) {
			etOut.add(new ErrorType(validDisambigOutJSON, "entities"));
		}

		if (validQuestion && validNed && validClasses && validScore && validProperties && validEntities) {
			return true;
		} else {
			return false;
		}

	}

}