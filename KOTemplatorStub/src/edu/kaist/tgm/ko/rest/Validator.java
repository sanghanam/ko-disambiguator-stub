package edu.kaist.tgm.ko.rest;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Validator {

	public ArrayList<ErrorType> etIn = new ArrayList<ErrorType>();
	public ArrayList<ErrorType> etOut = new ArrayList<ErrorType>();

	public boolean isValidTemplatorInJSON(String templatorInJSONStr) {

		JSONParser jsonParser = new JSONParser();

		JSONObject templatorInJSON = null;

		try {

			templatorInJSON = (JSONObject) jsonParser.parse(templatorInJSONStr);

		} catch (ParseException pe) {

			etIn.add(new ErrorType(false, "parse"));
			// System.err.println(pe.toString());
			// pe.printStackTrace();

			return false;

		}

		boolean validTemplatorInJSON = false;

		boolean validString = false;
		boolean validLanguage = false;

		try {

			validTemplatorInJSON = !templatorInJSON.isEmpty();
			validTemplatorInJSON = (templatorInJSON.size() != 0);
		} catch (NullPointerException npe) {

			// System.err.println(npe.toString());
			// npe.printStackTrace();

			return false;

		}
		try {
			validTemplatorInJSON = !templatorInJSON.get("string").equals("");
			validString = true;
		} catch (NullPointerException npeS) {
			etIn.add(new ErrorType(validTemplatorInJSON, "string"));
		}
		try {
			validTemplatorInJSON = !templatorInJSON.get("language").equals("");
			validLanguage = true;
		} catch (NullPointerException npeL) {
			etIn.add(new ErrorType(validTemplatorInJSON, "language"));
		}

		if (validString && validLanguage) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isValidTemplatorOutJSON(String templatorOutJSONStr) {

		JSONParser jsonParser = new JSONParser();

		JSONObject templatorOutJSON = null;

		try {

			templatorOutJSON = (JSONObject) jsonParser.parse(templatorOutJSONStr);

		} catch (ParseException pe) {

			etOut.add(new ErrorType(false, "parse"));
			// System.err.println(pe.toString());
			// pe.printStackTrace();

			return false;

		}

		boolean validTemplatorOutJSON = false;

		try {

			validTemplatorOutJSON = !templatorOutJSON.isEmpty();
			validTemplatorOutJSON = (templatorOutJSON.size() != 0);
		} catch (NullPointerException npe) {

			// System.err.println(npe.toString());
			// npe.printStackTrace();

			return false;

		}

		boolean validQuestion = false;
		boolean validQuery = false;
		boolean validSlots = false;
		boolean validScore = false;

		try {
			validTemplatorOutJSON = !templatorOutJSON.get("question").equals("");
			validQuestion = true;
		} catch (NullPointerException npeQ) {
			etOut.add(new ErrorType(validTemplatorOutJSON, "question"));
		}

		try {
			validTemplatorOutJSON = !templatorOutJSON.get("query").equals("");
			validQuery = true;
		} catch (NullPointerException npeQY) {
			etOut.add(new ErrorType(validTemplatorOutJSON, "query"));
		}

		try {
			validTemplatorOutJSON = !templatorOutJSON.get("slots").equals(null);
			validSlots = true;
		} catch (NullPointerException npeSC) {
			etOut.add(new ErrorType(validTemplatorOutJSON, "slots"));
		}

		try {
			validTemplatorOutJSON = !templatorOutJSON.get("score").equals("");
			validScore = true;
		} catch (NullPointerException npeSL) {
			etOut.add(new ErrorType(validTemplatorOutJSON, "score"));
		}

		if (validQuestion && validQuery && validSlots && validScore) {
			return true;
		} else {
			return false;
		}
	}

}