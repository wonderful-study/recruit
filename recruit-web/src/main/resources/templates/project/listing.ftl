<!DOCTYPE html>

<html lang="en-US">

<@common.header/>

<body class="page-sub-page page-listing-lines page-search-results" id="page-top">

<div class="wrapper">
    <@common.nav/>

    <div id="page-content">
        <div class="container">
            <ol class="breadcrumb">
                <li> <a href="#">Home</a> </li>
                <li class="active">项目列表 </li>
            </ol>
        </div>

        <div class="container">
            <div class="row">
                <div class="col-md-9 col-sm-9">
                    <section id="results">
                        <header><h1>项目列表</h1></header>
                        <section id="search-filter">
                            <figure><h3><i class="fa fa-search"></i>搜索结果 </h3>
                                <span class="search-count"></span>
                                <div class="sorting">
                                    <div class="form-group">
                                        <select name="sorting" id="sorting">
                                            <option value="">排序</option>
                                            <option value="price_asc" <#if (vo.sort) == "price_asc">selected</#if> >价格由低到高</option>
                                            <option value="price_desc" <#if (vo.sort) == "price_desc">selected</#if> >价格由高到低</option>
                                            <option value="time_desc" <#if (vo.sort) == "time_desc">selected</#if> >加入时间</option>
                                        </select>
                                    </div>
                                </div>
                            </figure>

                        </section>

                        <section id="properties" class="display-lines">
                            <#list ps.list as project>
                                <div class="property">
                                    <figure class="tag status">${(project.type)!}</figure>
                                    <div class="property-image">
                                        <figure class="ribbon">In Hold</figure>
                                        <a href="project/detail?id=${(project.id)!}">
                                            <img src="${(project.firstImg)!}" style="width:260px;height:195px">
                                        </a>
                                    </div>
                                    <div class="info">
                                        <header>
                                            <a href="/project/detail?id=${(project.id)!}"><h3>${(project.name)!}</h3></a>
                                            <figure>${(project.address)!}</figure>
                                        </header>
                                        <div class="tag price">￥${(project.price)!}元</div>
                                        <#--<aside>-->
                                        <#--<p>${(project.remarks)!}</p>-->
                                        <dl class="dl-horizontal">
                                           <dt>Status:</dt>
                                               <dd>Sale</dd>
                                           <dt>招聘人数</dt>
                                               <dd>${(project.recruitment)!}</dd>
                                           <dt>学历要求</dt>
                                               <dd>${(project.degree)!}</dd>
                                           <dt>语言要求</dt>
                                               <dd>${(project.language)!}</dd>
                                            <dt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dt>
                                            <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dd>
                                            <dt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dt>
                                            <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dd>
                                         </dl>
                                        <#--</aside>-->
                                        <a href="/project/detail?id=${(project.id)!}" class="link-arrow">Read More</a>
                                    </div>
                                </div>
                            </#list>

                            <div class="center">
                                <@common.paging ps.pagination/>
                            </div>

                        </section>
                    </section>
                </div> <!--result end col-md-9-->


                <div class="col-md-3 col-sm-3">
                    <section id="sidebar">
                        <aside id="edit-search">
                            <header><h3>Search Properties</h3></header>
                            <form role="form" id="searchForm" class="form-search" method="post" action="/project/list">
                                <div class="form-group">
                                    <input type="text" class="form-control" id="search-box-property-id" value="${(vo.name)!}" name="name" placeholder="尝试输入项目名">
                                </div>
                                <div class="form-group">
                                    <select name="type">
                                        <option value="">类型</option>
                                        <option value="1" <#if (vo.type)??&&(vo.type)==1>selected</#if> >上市公司</option>
                                        <option value="2" <#if (vo.type)??&&(vo.type)==2>selected</#if> >国企</option>
                                        <option value="3" <#if (vo.type)??&&(vo.type)==3>selected</#if> >私人公司</option>
                                    </select>
                                </div>
                                <input type="text" value="${(vo.sort)!}" name="sort" hidden="true">

                                <div class="form-group">
                                    <button type="submit" class="btn btn-default">搜索</button>
                                </div>
                            </form>
                        </aside>

                        <aside id="featured-properties">
                            <header><h3>热门项目</h3></header>
                            <<#list recomProjects as project>
                                <div class="property small">
                                    <a href="/project/detail?id=${(project.id)!}">
                                        <div class="property-image">
                                            <img src="${(project.firstImg)!}" alt="" style="width: 100px;height: 75px">
                                        </div>
                                    </a>
                                    <div class="info">
                                        <a href="/project/detail?id=${(project.id)!}"><h4>${(project.name)!}</h4></a>
                                        <figure>${(project.address)!}</figure>
                                        <div class="tag price">￥${(project.price)!}元</div>
                                    </div>
                                </div>
                            </#list>
                        </aside>

                    </section> <!--sidebar-->
                </div> <!--col-md-3-->

            </div>
        </div>


    </div>

    <@common.footer/>

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




    $('#sorting').change(function() {
        var type =  $(this).val();
        if (!type) {
            return;
        }
        window.location.href=  "/project/list?sort="+type+"&name=" + "${(vo.name)!}" + "&type=" + "${(vo.type)!0}" ;
    });


</script>




</body>




</html>