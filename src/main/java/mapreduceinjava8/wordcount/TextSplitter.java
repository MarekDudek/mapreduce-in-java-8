package mapreduceinjava8.wordcount;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.StringTokenizer;

public class TextSplitter {

    public String[] splitIntoArray(final String text) {

        final List<String> list = Lists.newArrayList();

        final StringTokenizer tokenizer = new StringTokenizer(text);
        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();
            list.add(token);
        }

        final String[] array = new String[list.size()];
        return list.toArray(array);
    }
}
