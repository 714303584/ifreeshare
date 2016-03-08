<!DOCTYPE html>
<html lang="en">
<head>
<title>节操在这里!!</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="img/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
<meta name="description" content="Your description">
<meta name="keywords" content="Your keywords">
<meta name="author" content="Your name">
<#include "linkcss.ftl"> 
</head>
<body class="home-page stretched page-grey">
	<div id="wrapper">
		<!--==============================header=================================-->
		<div class="header-block clearfix"  style="border-top: 0px;">
			<!-- open close panel -->
				<#include "openClosePanel.ftl"> 
			<!-- open close panel -->
			
			<!-- topbar -->
			<#include "topbar.ftl"> 
			<!-- topbar  end  -->
			<!-- Logo & Navigation -->
				<#include "top.ftl"> 
				
			<!-- Logo & Navigation  end -->
		</div>
		<!--============================== Slider =================================-->
		<#include "hidetop.ftl"> 
		<!--==============================content=================================-->
		<section id="content">
			<div class="container">
				<div class="row">
					<div class="title2">
						<h4 class="indent-1 extra" >文档分类</h4>
					</div>
					
					<div class="span4">
						<ul class="list-style-2">
							<#list docTypes as item>
								<li class="arrow-list-2"><a href="#">${item.displayName} </a></li>
								<#if item_index = 7>
									<#break>
								</#if>
							</#list>
						</ul>

					</div>
					<div class="span4">
						<ul class="list-style-2">
							<#list docTypes as item>
								<#if (item_index > 7)>
									<li class="arrow-list-2"><a href="#">${item.displayName}</a></li>
								</#if>
								
								<#if item_index = 15>
									<#break>
								</#if>
							</#list>
					
						</ul>
					</div>
					<div class="span4">
						<ul class="list-style-2">
						<#list docTypes as item>
								<#if (item_index > 15)>
									<li class="arrow-list-2"><a href="#">${item.displayName} </a></li>
								</#if>
								
								<#if item_index = 22>
									<#break>
								</#if>
							</#list>
							<li class="arrow-list-2"><a href="/list.html">更多</a></li>
						</ul>
					</div>
				</div>
			</div>

			<div class="section-4">
				<div class="container">
					<!-- latest posts -->
					<div class="title2">
						<div class="title-box clearfix">
							<h4 class="title-box_primary">热门下载</h4>
							<h3 class="title-box_secondary">这里有最热门的文档</h3>
						</div>
					</div>
					<div class="carousel-wrap carousel__formats">
						<div id="carousel-blog" class="es-carousel-wrapper">
							<div class="es-carousel">
								<ul class="es-carousel_list">
								
								<#list hotDownload as item>
									<li class="es-carousel_li gallery">
										<figure class="featured-thumbnail">
											<div class="img-border portfolio-overlay">
											<a href="/doc.html?md5=${item.md5}"><img src="/ifreeshare/docimg/${item.imgPath}"  width="205" height="290" alt="" /></a>
												
												<div class="portfolio-overlay-content">
													<h2>P.${item.pages}</h2>
													<span class="link-page"><a href="#"
														class="prettyPhoto"></a></span><span class="zoom-effect"><a
														href="${item.imgPath}" class="prettyPhoto"></a></span>
												</div>
											</div>
										</figure>
										<div class="carousel-proj-text">
											<h5>
												<a href="/doc.html?md5=${item.md5}" style="white-space: pre-wrap;word-wrap: break-word;">${item.fileName}</a>
											</h5>
											<p class="excerpt" style="white-space: pre-wrap;word-wrap: break-word;">${item.desc}</p>
										</div>
									</li>
								</#list>
								
									
									
									
									<li class="es-carousel_li gallery">
										<figure class="featured-thumbnail">
											<div class="img-border portfolio-overlay">
												<a href="/sidebar-right.html"><img src="img/es-carousel-img3.jpg"  width="205" height="290"  alt="" /></a>
												<div class="portfolio-overlay-content">
													<h2>Project Title</h2>
													<span class="link-page"><a href="#"
														class="prettyPhoto"></a></span><span class="zoom-effect"><a
														href="img/es-carousel-img3.jpg" class="prettyPhoto"></a></span>
												</div>
											</div>
										</figure>
										<div class="carousel-proj-text">
											<h5>
												<a href="#">更多</a>
											</h5>
											<p class="excerpt">获取更多热门文档</p>
										</div>
									</li>
								</ul>
							</div>
							<div class="es-nav">
								<span class="es-nav-prev"><i class="icon-chevron-left"></i></span><span
									class="es-nav-next"><i class="icon-chevron-right"></i></span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="container">
				<!-- client logo -->
				<div class="row">
					<div class="span12">
						<div class="title2">
							<h4 class="indent-1 extra">网站参与者</h4>
						</div>
						<ul class="list-banners shadow-large">
							<li><a href="#"><img src="img/shanzhu.png" alt="山竹"></a></li>
						</ul>
					</div>
				</div>
			</div>
		</section>
		<!--==============================footer=================================-->
		<#include "bot.ftl"> 
		<!--==============================footer=================================-->
	</div>
	<!--==============================Style Switcher =================================-->
	<!--<div class="switcher clearfix">
  <div class="switcher-head"> <span>Choose your Style</span>
    <div class="switcher-trigger icon-cog"></div>
  </div>
  <div class="switcher-body clearfix">
    <div class="switcher-op-layout">
      <select id="switcher-option-layout" name="switcher-option-layout" class="input-block-level nobottommargin">
        <option value="layout-boxed">Boxed</option>
        <option value="layout-full" selected="selected">Full Width</option>
      </select>
    </div>
    <div class="line"></div>
    <div class="switcher-op-colors">
      <p><strong>Predefined Colors:</strong></p>
      <div id="color-changer-box">
       
      </div>
      <small>* You can use Unlimited Colors</small> </div>
    <div class="line"></div>
    <div class="switcher-op-patterns">
      <p><strong>Predefined Patterns:</strong></p>
     
      <small>* Only for Boxed Layout</small> </div>
    <div class="line"></div>
    <div class="switcher-op-bgimages">
      <p><strong>Background Images:</strong></p>
     
      <small>* Only for Boxed Layout</small> </div>
    <div class="line"></div>
    <div id="switcher-option-footer" class="switcher-op-footerc clearfix"> <a href="#" data-color="dark" class="swfooter-active">Dark Footer</a> <a href="#" data-color="light">Light Footer</a> </div>
  </div>
</div>-->
<#include "linkjslib.ftl"> 
	<!-- Elastislider  -->
	<script>
		jQuery("#carousel-blog").elastislide({
			imageW : 218,
			minItems : 2,
			speed : 600,
			easing : "easeOutQuart",
			margin : 5,
			border : 0,
			onClick : function() {
			}
		});
	</script>
	<!-- Image animated Style  -->
	<script type="text/javascript">
		$(function() {

			$('#ri-grid').gridrotator({
				rows : 4,
				columns : 8,
				maxStep : 2,
				interval : 2000,
				w1024 : {
					rows : 5,
					columns : 6
				},
				w768 : {
					rows : 5,
					columns : 5
				},
				w480 : {
					rows : 6,
					columns : 4
				},
				w320 : {
					rows : 7,
					columns : 4
				},
				w240 : {
					rows : 7,
					columns : 3
				},
			});

		});
	</script>
	<!-- Metro Style  -->
	<script>
		$(function() {

			Boxgrid.init();

		});
	</script>
	<!-- Revolution Slider  -->
	<script>
		var api;
		jQuery(document).ready(function() {
			api = jQuery('.fullwidthabnner').revolution({
				delay : 9000,
				startheight : 400,
				startwidth : 1000,

				hideThumbs : 10,

				thumbWidth : 100, // Thumb With and Height and Amount (only if navigation Tyope set to thumb !)
				thumbHeight : 50,
				thumbAmount : 5,

				navigationType : "both", //bullet, thumb, none, both		(No Thumbs In FullWidth Version !)
				navigationArrows : "verticalcentered", //nexttobullets, verticalcentered, none
				navigationStyle : "round", //round,square,navbar

				touchenabled : "on", // Enable Swipe Function : on/off
				onHoverStop : "on", // Stop Banner Timet at Hover on Slide on/off

				navOffsetHorizontal : 0,
				navOffsetVertical : 20,

				stopAtSlide : -1,
				stopAfterLoops : -1,

				shadow : 1, //0 = no Shadow, 1,2,3 = 3 Different Art of Shadows  (No Shadow in Fullwidth Version !)
				fullWidth : "on" // Turns On or Off the Fullwidth Image Centering in FullWidth Modus
			});
		});

		function loadVideo() {
			jQuery("#video_link")
					.html(
							'iframe src="http//player.vimeo.com/video/32001208?title=0&amp;byline=0&amp;portrait=0" width="460" height="259"></iframe>');
		}
	</script>
	<div style="display: none">
		<script src='http//v7.cnzz.com/stat.php?id=155540&web_id=155540'
			language='JavaScript' charset='gb2312'></script>
	</div>
</body>
</html>
