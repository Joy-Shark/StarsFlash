//package com.huistar.huistarapps.Contraller.Other;
//
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class DownloadHeaderInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//
//        String requestURI = request.getRequestURI();
//        String[] parts = requestURI.split("/");
//        String fileName = parts[parts.length - 1]; // 获取最后一个部分作为文件名
//        // 设置Content-Disposition头的文件名
//        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//
//
//
//
//        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
//        String fileType = getContentTypeForFileExtension(fileExtension);
//
//        response.setHeader("Content-Type", fileType);
//        return true;
//    }
//
//    private String getContentTypeForFileExtension(String fileExtension) {
//        // 根据文件扩展名自适应设置Content-Type
//        switch (fileExtension) {
//            case "jpg":
//            case "jpeg":
//                return "image/jpeg";
//            case "mp3":
//                return "audio/mpeg";
//            case "png":
//                return "image/png";
//            case "pdf":
//                return "application/pdf";
//            default:
//                return "application/octet-stream"; // 默认类型，可以根据需要修改
//        }
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        // 在请求处理之后执行，可留空
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        // 在请求完成之后执行，可留空
//    }
//}
