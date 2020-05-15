/*
 * Copyright 2020 Toshiki Iga
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.igapyon.simplespel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * とてもシンプルな 関数組み込み付き SpEL 利用例.
 */
public class SimpleSpelSample01 {
    /**
     * 入力文字列.
     */
    public static final String INPUT_STRING = "#Abcdefg(3, 2)";

    /**
     * プログラムのエントリポイント。
     * 
     * @param args コマンドライン引数。
     */
    public static void main(String[] args) {
        final String inputString = INPUT_STRING;

        StandardEvaluationContext context = new StandardEvaluationContext();
        try {
            // 標準評価に関数を追加.
            context.registerFunction("Abcdefg", SimpleSpelSample01.class.getDeclaredMethod( //
                    "abcdefg", //
                    new Class[] { Integer.class, Integer.class }));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new IllegalArgumentException("Unexpected syntax error:" + e.toString());
        }

        ExpressionParser parser = new SpelExpressionParser();
        try {
            Expression exp = parser.parseExpression(inputString);
            Integer result = exp.getValue(context, Integer.class);
            System.err.println("result: " + result);
        } catch (SpelParseException e) {
            throw new IllegalArgumentException("与えられたSpEL式が不正です。[" + inputString + "]:" + e.toString());
        }
    }

    /**
     * SpEL に組み込みする関数のサンプル。
     * 
     * @param m 第一引数.
     * @param n 第二引数。
     * @return 計算結果。
     */
    public static int abcdefg(Integer m, Integer n) {
        if (m >= 1 && n >= 1) {
            return abcdefg(m - 1, abcdefg(m, n - 1));
        } else if (n == 0) {
            return abcdefg(m - 1, 1);
        } else {
            return n + 1;
        }
    }
}
