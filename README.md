# simple-spel

SpEL を自前プログラムに組み込んで利用する方法のシンプルサンプルです。

ちょっとしたプログラミング言語を自前プログラムに組み込みたい時に、SpEL を自前プログラムに組み込んで利用するのが良い方法の場合があります。

```java
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
```

