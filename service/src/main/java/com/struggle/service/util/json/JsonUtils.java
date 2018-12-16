package com.struggle.service.util.json;

import java.util.List;

public class JsonUtils {
    private static JsonOperator operator;
    private static boolean auto = false;
    public static synchronized void registerOperator(JsonOperator operator) {
        if (operator == null) {
            throw new NullPointerException();
        }
        if (JsonUtils.operator == operator) {
            return;
        }
        if (JsonUtils.operator != null) {
            if (!auto) {
                throw new JsonException("duplicate json operator");
            }
        }
        JsonUtils.operator = operator;
        JsonUtils.auto = false;
    }

    private static JsonOperator getOperator() {
        if (operator != null) {
            return operator;
        }

        synchronized (JsonUtils.class) {
            if (operator != null) {
                return operator;
            }
            try {
                operator = (FastJsonOperator)Class.forName("com.struggle.service.util.json.FastJsonOperator").newInstance();
                JsonUtils.auto = true;
            } catch (Exception e) {
                throw  new RuntimeException(e.getMessage(), e);
            }

            return operator;
        }
    }


    public static <T> T toObject(String json, Class<T> objClass) {
        return getOperator().toObject(json, objClass);
    }

    public static <T> List<T> toListObject(String json, Class<T> objClass) {
        return getOperator().toListObject(json, objClass);
    }

    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return getOperator().toJson(obj);
    }

}
