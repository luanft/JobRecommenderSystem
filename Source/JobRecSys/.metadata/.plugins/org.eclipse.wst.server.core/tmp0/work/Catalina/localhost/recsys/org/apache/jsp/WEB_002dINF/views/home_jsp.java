/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.36
 * Generated at: 2016-07-10 04:11:02 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import uit.se.recsys.bean.TaskBean;
import uit.se.recsys.bean.UserBean;
import java.util.List;

public final class home_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("jar:file:/E:/PROJECT/RESEARCH/Source/JobRecSys/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JobRecTaskManagement/WEB-INF/lib/spring-webmvc-4.3.0.RELEASE.jar!/META-INF/spring-form.tld", Long.valueOf(1465522608000L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1465637703417L));
    _jspx_dependants.put("/WEB-INF/lib/spring-webmvc-4.3.0.RELEASE.jar", Long.valueOf(1466303579195L));
    _jspx_dependants.put("jar:file:/E:/PROJECT/RESEARCH/Source/JobRecSys/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JobRecTaskManagement/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153359882000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("uit.se.recsys.bean.TaskBean");
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("uit.se.recsys.bean.UserBean");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005fform_0026_005fmodelAttribute_005fmethod_005fclass;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005finput_0026_005ftype_005frequired_005fpath_005fid_005fclass_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fid_005fclass;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fform_005fform_0026_005fmodelAttribute_005fmethod_005fclass = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fform_005finput_0026_005ftype_005frequired_005fpath_005fid_005fclass_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fid_005fclass = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fform_005fform_0026_005fmodelAttribute_005fmethod_005fclass.release();
    _005fjspx_005ftagPool_005fform_005finput_0026_005ftype_005frequired_005fpath_005fid_005fclass_005fnobody.release();
    _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fid_005fclass.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>Recommender</title>\r\n");
      out.write("\r\n");
      out.write("<!-- jquery -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"resources/js/jquery-2.0.0.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- bootstrap -->\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"resources/libs/bootstrap-3.3.6-dist/css/bootstrap.min.css\">\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"resources/libs/bootstrap-3.3.6-dist/js/bootstrap.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- custom css -->\r\n");
      out.write("<link rel=\"stylesheet\" href=\"resources/css/main.css\">\r\n");
      out.write("\r\n");
      out.write("<!-- custom query -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"resources/js/home.js\"></script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\t<!-- include header file -->\r\n");
      out.write("\t");

		UserBean user = (UserBean) session.getAttribute("user");
		if (user == null || user.getUserName() == null) {
	
      out.write('\r');
      out.write('\n');
      out.write('	');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "header.jsp", out, false);
      out.write('\r');
      out.write('\n');
      out.write('	');

		} else {
	
      out.write('\r');
      out.write('\n');
      out.write('	');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "loggedInHeader.jsp", out, false);
      out.write('\r');
      out.write('\n');
      out.write('	');

		}
	
      out.write("\r\n");
      out.write("\t<div class=\"container\">\r\n");
      out.write("\t\t<div class=\"row\" style=\"margin: 0 auto;\">\r\n");
      out.write("\t\t\t<div class=\"panel-group\">\r\n");
      out.write("\t\t\t\t<div class=\"panel panel-primary\">\r\n");
      out.write("\t\t\t\t\t<div class=\"panel-heading\" data-toggle=\"collapse\"\r\n");
      out.write("\t\t\t\t\t\tdata-target=\"#panel-content\">\r\n");
      out.write("\t\t\t\t\t\t<label class=\"text-uppercase\">Trạng thái tasks</label>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div id=\"panel-content\" class=\"panel-collapse collapse in\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"panel-body\">\r\n");
      out.write("\t\t\t\t\t\t\t<table class=\"table table-hover\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>STT</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>Tên task</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>Ngày tạo</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>Thuật toán</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>Dataset</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>Trạng thái</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</thead>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");

										List<TaskBean> listTask = (List<TaskBean>) request.getAttribute("listTask");
										int count = 1;
										for (TaskBean task : listTask) {
											out.write("<tr>");
											out.write("<td>" + count++ + "</td>");
											out.write("<td><a href='" + request.getContextPath() + "/ket-qua?taskid= " + task.getTaskId() + "'>"
													+ task.getTaskName() + "</a></td>");
											out.write("<td>" + task.getTimeCreate() + "</td>");
											out.write("<td>" + task.getAlgorithm() + "</td>");
											out.write("<td>" + task.getDataset() + "</td>");
											out.write("<td>" + task.getStatus() + "</td>");
										}
									
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<div class=\"panel panel-primary\">\r\n");
      out.write("\t\t\t\t\t<div class=\"panel-heading\" data-toggle=\"collapse\"\r\n");
      out.write("\t\t\t\t\t\tdata-target=\"#panel-content2\">\r\n");
      out.write("\t\t\t\t\t\t<label class=\"text-uppercase\">Tạo task mới</label>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div id=\"panel-content2\" class=\"panel-collapse collapse in\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"panel-body\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"col-md-3\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"col-md-6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      //  form:form
      org.springframework.web.servlet.tags.form.FormTag _jspx_th_form_005fform_005f0 = (org.springframework.web.servlet.tags.form.FormTag) _005fjspx_005ftagPool_005fform_005fform_0026_005fmodelAttribute_005fmethod_005fclass.get(org.springframework.web.servlet.tags.form.FormTag.class);
      _jspx_th_form_005fform_005f0.setPageContext(_jspx_page_context);
      _jspx_th_form_005fform_005f0.setParent(null);
      // /WEB-INF/views/home.jsp(99,9) null
      _jspx_th_form_005fform_005f0.setDynamicAttribute(null, "class", "form");
      // /WEB-INF/views/home.jsp(99,9) name = modelAttribute type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setModelAttribute("task");
      // /WEB-INF/views/home.jsp(99,9) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setMethod("POST");
      int[] _jspx_push_body_count_form_005fform_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_form_005fform_005f0 = _jspx_th_form_005fform_005f0.doStartTag();
        if (_jspx_eval_form_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t\t<label for=\"task\">Tên task</label>\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t\t");
            if (_jspx_meth_form_005finput_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return;
            out.write("\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t\t<label for=\"algorithm\">Chọn thuật toán</label>\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t\t");
            if (_jspx_meth_form_005fselect_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return;
            out.write("\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t\t<label for=\"algorithm\">Chọn hoặc <a\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t\t\thref=\"");
            out.print(request.getContextPath());
            out.write("/quan-ly-dataset\">nhập</a>\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t\t\tdataset\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t\t</label>\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t\t");
            //  form:select
            org.springframework.web.servlet.tags.form.SelectTag _jspx_th_form_005fselect_005f1 = (org.springframework.web.servlet.tags.form.SelectTag) _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fid_005fclass.get(org.springframework.web.servlet.tags.form.SelectTag.class);
            _jspx_th_form_005fselect_005f1.setPageContext(_jspx_page_context);
            _jspx_th_form_005fselect_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
            // /WEB-INF/views/home.jsp(119,11) null
            _jspx_th_form_005fselect_005f1.setDynamicAttribute(null, "class", "form-control");
            // /WEB-INF/views/home.jsp(119,11) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
            _jspx_th_form_005fselect_005f1.setId("dataset");
            // /WEB-INF/views/home.jsp(119,11) name = path type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
            _jspx_th_form_005fselect_005f1.setPath("dataset");
            int[] _jspx_push_body_count_form_005fselect_005f1 = new int[] { 0 };
            try {
              int _jspx_eval_form_005fselect_005f1 = _jspx_th_form_005fselect_005f1.doStartTag();
              if (_jspx_eval_form_005fselect_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n");
                  out.write("\t\t\t\t\t\t\t\t\t\t\t\t");

													String[] datasets = (String[]) request.getAttribute("datasets");
															if (datasets != null) {
																for (int i = 0; i < datasets.length; i++) {
																	out.write("<option value='" + datasets[i] + "'>" + datasets[i] + "</option>");
																}
															}
												
                  out.write("\r\n");
                  out.write("\t\t\t\t\t\t\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_form_005fselect_005f1.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_form_005fselect_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                return;
              }
            } catch (java.lang.Throwable _jspx_exception) {
              while (_jspx_push_body_count_form_005fselect_005f1[0]-- > 0)
                out = _jspx_page_context.popBody();
              _jspx_th_form_005fselect_005f1.doCatch(_jspx_exception);
            } finally {
              _jspx_th_form_005fselect_005f1.doFinally();
              _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fid_005fclass.reuse(_jspx_th_form_005fselect_005f1);
            }
            out.write("\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
            out.write("\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-primary\">Xử lý</button>\r\n");
            out.write("\t\t\t\t\t\t\t\t\t");
            int evalDoAfterBody = _jspx_th_form_005fform_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_form_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
      } catch (java.lang.Throwable _jspx_exception) {
        while (_jspx_push_body_count_form_005fform_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_form_005fform_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_form_005fform_005f0.doFinally();
        _005fjspx_005ftagPool_005fform_005fform_0026_005fmodelAttribute_005fmethod_005fclass.reuse(_jspx_th_form_005fform_005f0);
      }
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"col-md-3\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_form_005finput_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  form:input
    org.springframework.web.servlet.tags.form.InputTag _jspx_th_form_005finput_005f0 = (org.springframework.web.servlet.tags.form.InputTag) _005fjspx_005ftagPool_005fform_005finput_0026_005ftype_005frequired_005fpath_005fid_005fclass_005fnobody.get(org.springframework.web.servlet.tags.form.InputTag.class);
    _jspx_th_form_005finput_005f0.setPageContext(_jspx_page_context);
    _jspx_th_form_005finput_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
    // /WEB-INF/views/home.jsp(102,11) null
    _jspx_th_form_005finput_005f0.setDynamicAttribute(null, "type", "text");
    // /WEB-INF/views/home.jsp(102,11) null
    _jspx_th_form_005finput_005f0.setDynamicAttribute(null, "required", "required");
    // /WEB-INF/views/home.jsp(102,11) null
    _jspx_th_form_005finput_005f0.setDynamicAttribute(null, "class", "form-control");
    // /WEB-INF/views/home.jsp(102,11) name = path type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_form_005finput_005f0.setPath("taskName");
    // /WEB-INF/views/home.jsp(102,11) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_form_005finput_005f0.setId("taskName");
    int[] _jspx_push_body_count_form_005finput_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_form_005finput_005f0 = _jspx_th_form_005finput_005f0.doStartTag();
      if (_jspx_th_form_005finput_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_form_005finput_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_form_005finput_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_form_005finput_005f0.doFinally();
      _005fjspx_005ftagPool_005fform_005finput_0026_005ftype_005frequired_005fpath_005fid_005fclass_005fnobody.reuse(_jspx_th_form_005finput_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_form_005fselect_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  form:select
    org.springframework.web.servlet.tags.form.SelectTag _jspx_th_form_005fselect_005f0 = (org.springframework.web.servlet.tags.form.SelectTag) _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fid_005fclass.get(org.springframework.web.servlet.tags.form.SelectTag.class);
    _jspx_th_form_005fselect_005f0.setPageContext(_jspx_page_context);
    _jspx_th_form_005fselect_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
    // /WEB-INF/views/home.jsp(107,11) null
    _jspx_th_form_005fselect_005f0.setDynamicAttribute(null, "class", "form-control");
    // /WEB-INF/views/home.jsp(107,11) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_form_005fselect_005f0.setId("algorithm");
    // /WEB-INF/views/home.jsp(107,11) name = path type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_form_005fselect_005f0.setPath("algorithm");
    int[] _jspx_push_body_count_form_005fselect_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_form_005fselect_005f0 = _jspx_th_form_005fselect_005f0.doStartTag();
      if (_jspx_eval_form_005fselect_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"cf\">Collaborative Filtering</option>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"cb\">Content Base</option>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"hb\">Hybrid</option>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_form_005fselect_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_form_005fselect_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_form_005fselect_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_form_005fselect_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_form_005fselect_005f0.doFinally();
      _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fid_005fclass.reuse(_jspx_th_form_005fselect_005f0);
    }
    return false;
  }
}
