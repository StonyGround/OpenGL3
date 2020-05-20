package com.leer.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;

import com.leer.lib.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForbidEmojiEditText extends ObserverEditText {

    private boolean isSpanFilter;
    protected int maxLen;

    public ForbidEmojiEditText(Context context) {
        super(context);
    }

    public ForbidEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ForbidEmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ForbidEmojiEditText);
        isSpanFilter = mTypedArray.getBoolean(R.styleable.ForbidEmojiEditText_spanFilter,
                true);
        maxLen = mTypedArray.getInt(R.styleable.ForbidEmojiEditText_maxWordNumber, 20);
        mTypedArray.recycle();

        //重新设置filter
        setFilters(getMyFilters());
    }

    InputFilter[] getMyFilters() {
        //emoji过滤
        InputFilter emojiFilter = (source, start, end, dest, dstart, dend) -> {
            Pattern emoji = Pattern.compile(
                    "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                return "";
            }
            return null;
        };
        //特殊字符过滤器
        InputFilter specialCharFilter = (source, start, end, dest, dstart, dend) -> {
            String regexStr = "[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()" +
                    "<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` " +
                    "，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]";
            Pattern pattern = Pattern.compile(regexStr);
            Matcher matcher = pattern.matcher(source.toString());
            if (matcher.matches()) {
                return "";
            } else {
                return null;
            }
        };

        //长度限制
        InputFilter lengthFilter = new InputFilter.LengthFilter(maxLen);

        //空格过滤
        if (isSpanFilter) {
            InputFilter spanFilter = (source, start, end, dest, dstart, dend) -> {
                if (source.equals(" ") || source.toString().contentEquals("\n")) {
                    return "";
                } else {
                    return null;
                }
            };
            return new InputFilter[]{emojiFilter, specialCharFilter, spanFilter,
                    lengthFilter};
        }
        return new InputFilter[]{emojiFilter, specialCharFilter, lengthFilter};
    }

}
