<!DOCTYPE html>

<html lang="en-US">
<@common.header/>

<body class="page-sub-page page-property-detail" id="page-top">
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
                <li class="active">Property Detail</li>
            </ol>
        </div>
        <!-- end Breadcrumb -->

        <div class="container">
            <div class="row">
                <!-- Property Detail Content -->
                <div class="col-md-9 col-sm-9">
                    <section id="property-detail">
                        <header class="property-title">
                            <h1>${(project.name)!}</h1>
                            <figure>${(project.address)!}</figure>

                        <#if loginUser??>
                            <span class="actions">
                                <!--<a href="#" class="fa fa-print"></a>-->
                                <a href="#" class="bookmark" data-bookmark-state="empty"

                                ><span class="title-add">Add to bookmark</span><span class="title-added">Added</span></a>
                            </span>
                        </#if>
                        </header>
                        <section id="property-gallery">
                            <div class="owl-carousel property-carousel">
                                <#list project.imageList as image>
                                    <div class="property-slide">
                                        <a href="${(image)!}" class="image-popup">
                                            <div class="overlay"><h3>Front View</h3></div>
                                            <img alt="" src="${(image)!}" height="400" >
                                        </a>
                                    </div><!-- /.property-slide -->
                                </#list>
                            </div><!-- /.property-carousel -->
                        </section>
                        <div class="row">
                            <div class="col-md-4 col-sm-12">
                                <section id="quick-summary" class="clearfix">
                                    <header><h2>总体</h2></header>
                                    <dl>
                                        <dt>地址</dt>
                                        <dd>${(project.address)!}</dd>
                                        <dt>价格</dt>
                                        <dd><span class="tag price">${(project.price)!}</span></dd>
                                        <dt>类型:</dt>
                                        <dd>发布</dd>
                                        <dt>招聘人数:</dt>
                                        <dd>${(project.recruitment)!}</dd>
                                        <dt>学历要求:</dt>
                                        <dd>${(project.degree)!}</dd>
                                        <dt>语言要求:</dt>
                                        <dd>${(project.language)!}</dd>
                                        <dt>评分:</dt>
                                        <dd><div class="rating rating-overall" data-score="${(project.rating)!}"></div></dd>
                                    </dl>
                                </section><!-- /#quick-summary -->
                            </div><!-- /.col-md-4 -->
                            <div class="col-md-8 col-sm-12">
                                <section id="description">
                                    <header><h2>项目描述</h2></header>
                                    <textarea class="form-control" rows="8"  style="resize:none ;cursor: auto;" readonly>${(project.remarks)!}</textarea>
                                </section><!-- /#description -->

                                <section id="property-rating">
                                    <header><h2>评价</h2></header>
                                    <div class="clearfix">
                                        <aside>
                                            <header>您的评价</header>
                                            <div class="rating rating-user">
                                                <div class="inner"></div>
                                            </div>
                                        </aside>
                                        <figure>
                                            <header>总体评价</header>
                                            <div class="rating rating-overall" data-score="${(project.rating)!}"></div>
                                        </figure>
                                    </div>
                                    <div class="rating-form">
                                    </div><!-- /.rating-form -->
                                </section><!-- /#property-rating -->



                            </div><!-- /.col-md-8 -->

                            <div class="col-md-12 col-sm-12">
                                <#if (agent)?? >
                                   <section id="contact-agent">
                                       <header><h2>联系人事</h2></header>
                                       <div class="row">
                                           <section class="agent-form">
                                               <div class="col-md-7 col-sm-12">
                                                   <aside class="agent-info clearfix">
                                                       <figure><a href="/agency/agentDetail?id=${(agent.id)!}"><img alt="" src="${(agent.avatar)!}"></a></figure>
                                                       <div class="agent-contact-info">
                                                           <h3>${(agent.name)!}</h3>
                                                           <p>
                                                               ${(agent.aboutme)!}
                                                           </p>
                                                           <dl>
                                                               <dt>手机:</dt>
                                                               <dd>${(agent.phone)!}</dd>
                                                               <dt>Email:</dt>
                                                               <dd><a href="mailto:#">${(agent.email)!}</a></dd>
                                                               <dt>&nbsp;&nbsp;&nbsp;</dt>
                                                               <dd>&nbsp;&nbsp;&nbsp;</dd>
                                                           </dl>
                                                           <hr>
                                                       </div>
                                                   </aside><!-- /.agent-info -->
                                               </div><!-- /.col-md-7 -->
                                               <div class="col-md-5 col-sm-12">
                                                   <div class="agent-form">
                                                       <form role="form" id="form-contact-agent" method="post" action="/project/leaveMsg" class="clearfix">
                                                           <div class="form-group">
                                                               <label for="form-contact-agent-name">你的名字<em>*</em></label>
                                                               <input type="hidden" name="agentId" value="${(agent.id)!}">
                                                               <input type="hidden" name="projectId" value="${(project.id)!}">
                                                               <input type="text" class="form-control" id="name" name="userName" required>
                                                           </div><!-- /.form-group -->
                                                           <div class="form-group">
                                                               <label for="form-contact-agent-email">你的邮箱<em>*</em></label>
                                                               <input type="email" class="form-control" id="form-contact-agent-email" name="email" required>
                                                           </div><!-- /.form-group -->
                                                           <div class="form-group">
                                                               <label for="form-contact-agent-message">你的留言<em>*</em></label>
                                                               <textarea class="form-control" id="form-contact-agent-message" rows="2" name="msg" required></textarea>
                                                           </div><!-- /.form-group -->
                                                           <div class="form-group">
                                                               <button type="submit" class="btn pull-right btn-default" id="form-contact-agent-submit">Send a Message</button>
                                                           </div><!-- /.form-group -->
                                                           <div id="form-contact-agent-status"></div>
                                                       </form><!-- /#form-contact -->
                                                   </div><!-- /.agent-form -->
                                               </div><!-- /.col-md-5 -->
                                           </section><!-- /.agent-form -->
                                       </div><!-- /.row -->
                                   </section><!-- /#contact-agent -->
                                </#if>

                                <#--<hr class="thick">
                                <section id="comments">
                                    <div class="agent-form">
                                        <form role="form" id="form-contact-agent" method="post" action="/comment/leaveComment" class="clearfix">
                                            <input type="hidden" name="projectId" value="${(project.id)!}">
                                            <div class="form-group">
                                                <label for="form-contact-agent-message">评论</label>
                                                <textarea class="form-control" id="form-contact-agent-message" rows="2" name="content" required></textarea>
                                            </div><!-- /.form-group &ndash;&gt;
                                            <div class="form-group">
                                                <button type="submit" class="btn pull-right btn-default" id="form-contact-agent-submit">评论</button>
                                            </div><!-- /.form-group &ndash;&gt;
                                            <div id="form-contact-agent-status"></div>
                                        </form><!-- /#form-contact &ndash;&gt;
                                    </div>
                                    <header><h2 class="no-border">Comments</h2></header>
                                    <ul class="comments">
                                      <#list commentList as comment>
                                          <li class="comment" style="width: 830px;">
                                              <figure>
                                                  <div class="image">
                                                      <img alt="" src="${(comment.avatar)!}">
                                                  </div>
                                              </figure>
                                              <div class="comment-wrapper">
                                                  <div class="name pull-left">${(comment.userName)!}</div>
                                                  <span class="date pull-right"><span class="fa fa-calendar"></span>${(comment.createTime)?datetime}</span>
                                                  <p>${comment.content}
                                                  </p>
                                                  <hr>
                                              </div>
                                          </li>
                                      </#list>
                                    </ul>
                                </section>-->
                            </div><!-- /.col-md-12 -->

                        </div><!-- /.row -->
                    </section><!-- /#property-detail -->
                </div><!-- /.col-md-9 -->
                <!-- end Property Detail Content -->

                <!-- sidebar -->
                <div class="col-md-3 col-sm-3">
                    <section id="sidebar">
                        <aside id="edit-search">
                            <header><h3>搜索项目</h3></header>
                            <form role="form" id="searchForm" class="form-search" method="post" action="/project/list">

                                <div class="form-group">
                                    <input type="text" class="form-control" id="search-box-property-id" value="${(vo.name)!}" name="name" placeholder="尝试输入项目名">
                                </div>
                                <div class="form-group">
                                    <select name="type">
                                        <!-- <option value="1">类型</option>  -->
                                        <option value="1" <#if (vo.type)??&&(vo.type)==1>selected</#if> >上市公司</option>
                                        <option value="2" <#if (vo.type)??&&(vo.type)==2>selected</#if> >国企</option>
                                        <option value="3" <#if (vo.type)??&&(vo.type)==3>selected</#if> >私人公司</option>
                                    </select>
                                </div>
                                <input type="text" value="${(vo.sort)!}" name=sort hidden="true">

                                <div class="form-group">
                                    <button type="submit" class="btn btn-default">搜索</button>
                                </div><!-- /.form-group -->
                            </form><!-- /#form-map -->
                        </aside><!-- /#edit-search -->
                        <aside id="featured-properties">
                            <header><h3>热门项目</h3></header>
                            <#list recomProjects as project>
                            <div class="property small">
                                <a href="/project/detail?id=${(project.id)!}">
                                    <div class="property-image">
                                        <img alt="" src="${(project.firstImg)!}" style="width: 100px;height: 75px">
                                    </div>
                                </a>
                                <div class="info">
                                    <a href="/project/detail?id=${(project.id)!}"><h4>${(project.name)!}</h4></a>
                                    <figure>${(project.address)!} </figure>
                                    <div class="tag price">￥${(project.price)!}</div>
                                </div>
                            </div><!-- /.property &ndash;&gt;-->
                            </#list>
                        </aside><!-- /#featured-properties -->

                    </section><!-- /#sidebar -->
                </div><!-- /.col-md-3 -->
                <!-- end Sidebar -->
            </div><!-- /.row -->
        </div><!-- /.container -->
    </div>
    <!-- end Page Content -->
    <!-- Page Footer -->
    <@common.footer/>
    <!-- end Page Footer -->
</div>
<!-- <script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyABT1kCnk8CW4Ckpd0RisUg25PIdDD3Gfg"></script> -->

<@common.js/>

<!--[if gt IE 8]>
<script type="text/javascript" src="/static//js/ie.js"></script>
<![endif]-->
<script  type="text/javascript" >



    $(window).load(function(){
        initializeOwl(false);
    });


    $(document).ready(function() {
        var errorMsg   = "${errorMsg!""}";
        var successMsg = "${successMsg!""}";
        if(errorMsg){
            errormsg("error",errorMsg);
        }
        if(successMsg) {
            successmsg("success",successMsg);
        }

        var ratingUser = $('.rating-user');
        if (ratingUser.length > 0) {
            $('.rating-user .inner').raty({
                path: '/static/assets/img',
                starOff : 'big-star-off.png',
                starOn  : 'big-star-on.png',
                width: 150,
                //target : '#hint',
                targetType : 'number',
                targetFormat : 'Rating: {score}',
                click: function(score, evt) {
                    showRatingForm();
                    $.ajax({
                        url: "/project/rating?id=${(project.id)!}&rating="+score,
                        type: 'GET',
                        cache:false,
                        timeout:60000
                    })
                            .done(function(ret) {

                            })
                }
            });
        }
    })

    var bookmarkButton = $(".bookmark");


    bookmarkButton.on("click", function() {
        if (bookmarkButton.data('bookmark-state') == 'empty') {
            commonAjax('/project/bookmark?id=${(project.id)!}');
        } else if (bookmarkButton.data('bookmark-state') == 'added') {
            commonAjax('/project/unbookmark?id=${(project.id)!}');
        }
    });




</script>

</body>
</html>