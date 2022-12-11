package com.bj.search.service.Levenshtein;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
public interface LevenshteinService {

    JsonNode search(Object wordsA, Object wordsB) throws IOException;
}
