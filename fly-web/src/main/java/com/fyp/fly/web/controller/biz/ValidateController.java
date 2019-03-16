package com.fyp.fly.web.controller.biz;

import com.fyp.fly.web.clients.base.BaseApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author fyp
 * @crate 2019/3/14 21:55
 * @project fly
 */
@Controller
@RequestMapping("/validate")
public class ValidateController extends BaseController {
    @Autowired
    private BaseApiClient baseApiClient;

    @GetMapping("/code")
    public void test(HttpServletResponse response) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = baseApiClient.getValidateCode();
            out = response.getOutputStream();
            int length;
            byte[] buffer = new byte[1024];
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
