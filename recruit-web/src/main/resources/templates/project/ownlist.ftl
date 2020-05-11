<!DOCTYPE html>

<html lang="en-US">
<@common.header/>

<body class="page-sub-page page-profile page-account" id="page-top">
<!-- Wrapper -->
<div class="wrapper">
    <!-- Navigation -->
   <@common.nav/><!-- /.navigation -->
    <!-- end Navigation -->
    <!-- Page Content -->
    <div id="page-content">
        <!-- Breadcrumb -->
        <div class="container">
            <ol class="breadcrumb">
                <li><a href="#">Home</a></li>
                <li><a href="#">Account</a></li>
                <li class="active">Profile</li>
            </ol>
        </div>
        <!-- end Breadcrumb -->

         <div class="container">
            <div class="row">
            <!-- sidebar -->
            <div class="col-md-3 col-sm-2">
                <section id="sidebar">
                    <header><h3>账号</h3></header>
                    <aside>
                        <ul class="sidebar-navigation">
                            <li><a href="/accounts/profile"><i class="fa fa-user"></i><span>个人信息</span></a></li>
                            <li <#if  pageType != "book">  class="active" </#if> ><a href="/project/ownlist"><i class="fa fa-home"></i><span>我的项目信息</span></a></li>
                            <li <#if  pageType == "book"> class="active" </#if>><a href="/project/bookmarked"><i class="fa fa-heart"></i><span>项目收藏 </span></a></li>
                        </ul>
                    </aside>
                </section><!-- /#sidebar -->
            </div><!-- /.col-md-3 -->
            <!-- end Sidebar -->
                <!-- My Properties -->
                <div class="col-md-9 col-sm-10">
                    <section id="my-properties">
                        <header><h1><#if  pageType == "book"> 我的收藏 <#else> 我的项目</#if></h1></header>
                        <div class="my-properties">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>项目</th>
                                        <th></th>
                                        <th>创建时间</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                 <#list ps.list as project>
                                    <tr>
                                        <td class="image">
                                            <a href="/project/detail?id=${(project.id)!}"><img alt="" src="${(project.firstImg)!}" style="width: 105px;height: 78px"></a>
                                        </td>
                                        <td><div class="inner">
                                            <a href="/project/detail?id=${(project.id)!}"><h2>${(project.name)!}</h2></a>
                                            <figure>${(project.address)!}</figure>
                                            <div class="tag price">${(project.price)!}</div>
                                        </div>
                                        </td>
                                        <td>${(project.createTime?datetime)}</td>
                                        <td><#if project.state==1>上架<#else>下架</#if></td>
                                        <td class="actions">
                                            <a href="/project/del?id=${(project.id)!}&pageType=${pageType}"><i class="delete fa fa-trash-o"></i></a>
                                        </td>
                                    </tr>
                                 </#list>
                                </tbody>
                              </table>
                         </div><!-- /.table-responsive -->
                            <!-- Pagination -->
                            <div class="center">
                                  <@common.paging ps.pagination/>
                            </div><!-- /.center-->
                        </div><!-- /.my-properties -->
                    </section><!-- /#my-properties -->
                </div><!-- /.col-md-9 -->
                <!-- end My Properties -->
            </div><!-- /.row -->
        </div><!-- /.container -->
    </div>
    <!-- end Page Content -->
    <!-- Page Footer -->
    <@common.footer/>
    <!-- end Page Footer -->
</div>

<@common.js/>
<!--[if gt IE 8]>
<script type="text/javascript" src="assets/js/ie.js"></script>
<![endif]-->
 <script  type="text/javascript" >
     

     $(document).ready(function() {
          var errorMsg   = "${errorMsg!""}";
          var successMsg = "${successMsg!""}";
          if(errorMsg){ 
              errormsg("error",errorMsg);
          }
          if(successMsg) {
              successmsg("success",successMsg);
          }
        })
        
 </script>
</body>
</html>