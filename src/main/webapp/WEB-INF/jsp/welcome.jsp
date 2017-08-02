<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en" ng-app="springbootapp">
<head>

<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>

<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />


<c:url value="/js/controller.js" var="jstljs" />
<script src="${jstljs}" type="text/javascript"></script>


</head>
<body ng-controller="springbootappcontroller">

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Spring Boot</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse"></div>
		</div>
	</nav>

	<div class="container">

		<div class="starter-template">

			<form name="readText" ng-submit="readTextFile()">
				<input class="btn btn-default" type="submit" value="Read Text File" style="width: 200px;"/>
			</form>
			<form name="readText" ng-submit="getDomains()">
				<input class="btn btn-default" type="submit" value="JMX" style="width: 200px;"/>
			</form>





			<div class="panel panel-info" ng-class="textfiledata"
				ng-show="textfiledata" style="margin-top: 50px;">
				<div class="panel-body">Text File content</div>
				<div class="panel-footer">
				<div ng-repeat="tfd in textfiledata">{{tfd}}</div>
				</div>
			</div>



			<div ng-show="domains">
				<select class="form-control" style="width: 50%; margin-top: 50px;" ng-change="getTypes()"  ng-model="domainid">
					<option selected="true">-Select Domain-</option>
					<option ng-repeat="domain in domains">{{domain}}</option>
				</select>
			</div>


			<div ng-show="domainTypes">
				<select class="form-control" ng-model="domaintypeid" style="width: 50%; margin-top: 10px;"
					ng-change="getNames()">
					<option selected="true">-Select Type-</option>
					<option ng-repeat="dtype in domainTypes">{{dtype}}</option>
				</select>
			</div>

			<div ng-show="domainNames">
				<select class="form-control" ng-model="domainnameid" style="width: 50%; margin-top: 10px;"
					ng-change="getAttributes()">
					<option selected="true">-Select Names-</option>
					<option ng-repeat="dnames in domainNames">{{dnames}}</option>
				</select>
			</div>
			
			<div ng-show="domainAttributes">
				<select class="form-control" ng-model="domainAttributesid" style="width: 50%; margin-top: 10px;"
					ng-change="getAv()">
					<option selected="true">-Select Attribute-</option>
					<option ng-repeat="dattri in domainAttributes">{{dattri}}</option>
				</select>
			</div>
			
			<br><br>
			
			<div ng-show="attributevalue">
			<h3>{{attributevalue}}</h3>
			</div>
			
			<div ng-show="attributes">
			<h4 ng-repeat="attribute in attributes">{{attribute}}</h4>
			</div> 
			
			<div ng-show="error">
			<h4 style="color: red;">{{error}}</h4>
			</div> 
		</div>

	</div>

	<script type="text/javascript"
		src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>