package com.leer.lib.constants;

/**
 * String code = "ERROR_204"; ERROR_MSG msg = ERROR_MSG.valueOf(code); msg.getBewrite()
 */
public enum ERROR_MSG {
    ERROR_200("OK,一切正常"),
    ERROR_203("password input error"),
    ERROR_204("toeken 验证失败"),
    ERROR_205("参数提交错误或者缺少提交参数，错误提示将会在msg段中以对象的形式存放"),
    ERROR_206("用户不存在"),
    ERROR_207("发送邮件失败，用户邮箱无效或者被拦截等"),
    ERROR_208("不要重复操作"),

    ERROR_304("资源没有被修改。可以使用缓存的版本"),

    ERROR_400("错误的请求。可能通过用户方面的多种原因引起的，例如在请求体内有无效的JSON 数据，无效的操作参数，等等"),
    ERROR_401("验证失败"),
    ERROR_403("已经经过身份验证的用户不允许访问指定的 API 末端"),
    ERROR_404("所请求的资源不存在"),
    ERROR_405("不被允许的方法。 请检查 Allow header 允许的HTTP方法"),
    ERROR_415("不支持的媒体类型。 所请求的内容类型或版本号是无效的"),
    ERROR_422("数据验证失败 (例如，响应一个 POST 请求)。 请检查响应体内详细的错误消息"),
    ERROR_429("请求过多。 由于限速请求被拒绝"),

    ERROR_500("内部服务器错误。 这可能是由于内部程序错误引起的"),
    ;
    ERROR_MSG(String description){
        this.description = description;
    }
    private String description;

    public String getDescription() {
        return description;
    }

}
