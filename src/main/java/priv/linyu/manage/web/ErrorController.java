package priv.linyu.manage.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: ErrorController
 * @author: QiuShangLin
 * @description: 错误页面视图控制器
 * @date: 2019/7/19 0019 21:20
 * @version: 1.0
 **/
@Controller
@RequestMapping("/error")
public class ErrorController {

    /**
     * 401页面
     * @return
     */
    @GetMapping(value = "/401")
    public String error_401() {
        return "error/error_401";
    }

    /**
     * 404页面
     * @return
     */
    @GetMapping(value = "/404")
    public String error_404() {
        return "error/error_404";
    }

    /**
     * 500页面
     * @return
     */
    @GetMapping(value = "/500")
    public String error_500() {
        return "error/error_500";
    }

}
