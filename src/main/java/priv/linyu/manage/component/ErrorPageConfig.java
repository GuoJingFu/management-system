package priv.linyu.manage.component;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @className: ErrorPageConfig
 * @author: QiuShangLin
 * @description:
 * @date: 2019/7/19 0019 21:16
 * @version: 1.0
 **/
@Component
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {

        // 错误类型为401（无访问权限），显示401.html页面
        ErrorPage errorPage401 = new ErrorPage(HttpStatus.UNAUTHORIZED,"/error/401");

        // 错误类型为404（找不到资源），显示404.html页面
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,"/error/404");

        // 错误类型为500（服务器内部错误），显示500.html页面
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error/500");

        registry.addErrorPages(errorPage401,errorPage404,errorPage500);

    }
}
