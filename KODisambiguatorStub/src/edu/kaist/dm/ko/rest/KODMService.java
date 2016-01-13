package edu.kaist.dm.ko.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

@Path("/ko/dm/stub/service")
public class KODMService {
	
	@GET
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public void getGet(String input) throws JSONException {
		getPost(input);
	}

	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Response getPost(String input) throws JSONException {

		JSONObject error = new JSONObject();
		String result;

		Validator v = new Validator();
		boolean isValidInput = v.isValidDisambigInJSON(input);

		String sampleDisambigResult = null;

		// Input of Disambiguator (= output of Templator) is valid form.
		if (isValidInput) {
			// Call disambiguation module...

			// Get output of Disambiguator
			sampleDisambigResult = "{ \"question\": \"어떤 강이 서울을 흐르는가?\", \"ned\": [ { \"classes\": [ { \"var\": \"v1\", \"score\": 0.25, \"value\": \"http://dbpedia.org/ontology/River\" }, { \"var\": \"v1\", \"score\": 0.2564102564102564, \"value\": \"http://dbpedia.org/ontology/River\" } ], \"score\": 1, \"properties\": [ { \"var\": \"v3\", \"score\": 0.2564102564102564, \"value\": \"http://dbpedia.org/ontology/city\" } ], \"entities\": [ { \"var\": \"v5\", \"score\": 1, \"value\": \"http://ko.dbpedia.org/resource/서울특별시\" } ] } ] }";

			// Check output validation
			boolean isValidOutput = v.isValidDisambigOutJSON(sampleDisambigResult);

			// Output of Disambiguator is valid form.
			if (isValidOutput) {

				result = sampleDisambigResult;
			}
			// Output of Disambiguator is invalid form.
			else {
				error.put("error", "output");
				JSONArray errorCause = new JSONArray();
				for (int i = 0; i < v.etOut.size(); i++) {
					errorCause.put(v.etOut.get(i).getType());
				}
				error.put("error_cause", errorCause);
				result = error.toJSONString();
			}

		}
		// Input of Disambiguator (= output of Templator) is invalid form.
		else {
			error.put("error", "input");
			JSONArray errorCause = new JSONArray();
			for (int i = 0; i < v.etIn.size(); i++) {
				errorCause.put(v.etIn.get(i).getType());
			}
			error.put("error_cause", errorCause);
			result = error.toJSONString();
		}
		
		
		return Response.ok(result).entity(result)
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept").build();

	}

	@OPTIONS
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Response getOptions(String input) throws JSONException {

		return Response.ok().header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept").build();

	}

}
