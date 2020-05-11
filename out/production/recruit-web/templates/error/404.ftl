<!DOCTYPE html>

<html lang="en-US" >
<@common.header/>

<body class="page-sub-page page-blog-detail" id="page-top">

<div class = "wrapper">

    <@common.nav/>

    <div id="page-content">
        <div class="container">
            <ol class="breadcrumb">
                <li><a href="#">Home</a> </li>
                <li class="active">404</li>
            </ol>
        </div>

        <div class="container">
            <section id="404">
                <div class="error-page">
                    <div class="title">
                        <img alt="" src="/static/assets/img/error-page-background.png" class="top">
                        <header>404</header>
                        <img alt="" src="/static/assets/img/error-page-background.png" class="bottom">
                    </div>
                    <h2 class="no-border">页面未找到</h2>
                    <a class="link-arrow back" href="index">返回首页</a>
                </div>
            </section>
        </div>
    </div>


    <@common.footer/>

</div>

    <@common.js/>

<script>
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
    })
</script>

</body>
</html>