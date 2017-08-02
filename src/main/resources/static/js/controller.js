(function() {

	var app = angular.module("springbootapp", []);
	var homectrl = function($scope, $http, $interval, $log) {

		var url = "http://localhost:8080";

		var onError = function(reason) {
			$scope.error = reason;
		}
		/*
		 * $scope.readTextFile = function() { $log.info("Reading Text File");
		 * $http({ url : url + '/readText', method : 'GET', transformResponse : [
		 * function(data) { $log.info("data :"+data);
		 * 
		 * delete $scope.domains; delete $scope.domainTypes; delete
		 * $scope.domainNames; delete $scope.domainAttributes; delete
		 * $scope.error; delete $scope.attributes; delete $scope.textfiledata;
		 * delete $scope.attributevalue; $scope.textfiledata = data; return
		 * data; } ] }); }
		 */

		$scope.readTextFile = function() {
			$log.info("Reading Domain File");
			$http.get(url + "/readText").then(onreadTextComplete, onError);
		}
		var onreadTextComplete = function(response) {
			$log.info(response);

			delete $scope.domains;
			delete $scope.domainTypes;
			delete $scope.domainNames;
			delete $scope.domainAttributes;
			delete $scope.error;
			delete $scope.attributes;
			delete $scope.textfiledata;
			delete $scope.attributevalue;
			
			if (response.data.length === 0) {
				$log.info("Text File is empty");
				$scope.error = "Sorry. Text file is empty.";
			} else {
				$scope.textfiledata = response.data;
			}
		}

		$scope.getDomains = function() {
			$log.info("Reading Domain File");
			$http.get(url + "/getDomains").then(onDomainReadComplete, onError);
		}
		var onDomainReadComplete = function(response) {
			$log.info(response.data);

			delete $scope.domains;
			delete $scope.domainTypes;
			delete $scope.domainNames;
			delete $scope.domainAttributes;
			delete $scope.error;
			delete $scope.attributes;
			delete $scope.textfiledata;

			$scope.domains = response.data;
		}

		$scope.getTypes = function() {
			$log.info("Reading Type File for Domain " + $scope.domainid);
			$http.post(url + "/domain/"+$scope.domainid+"/getTypes").then(
					onTypeReadComplete, onError);
		}

		var onTypeReadComplete = function(response) {
			$log.info(response.data);

			delete $scope.domainTypes;
			delete $scope.domainNames;
			delete $scope.domainAttributes;
			delete $scope.error;
			delete $scope.attributes;
			delete $scope.textfiledata;

			$scope.domainTypes = response.data;
		}

		$scope.getNames = function() {
			$log.info("Reading names File for Domain " + $scope.domainid
					+ " Type " + $scope.domaintypeid);
			$http.post(url + "/domain/"+$scope.domainid+"/type/"+$scope.domaintypeid+"/getNames").then(onNameReadComplete, onError);
		}
		var onNameReadComplete = function(response) {
			$log.info(response.data);
			$log.info("length=" + response.data[0]);
			if (response.data[0] === null) {
				$log.info("No names declared...");
				$http.post(url + "/domain/"+$scope.domainid+"/type/"+$scope.domaintypeid+"/name/null/getAttributes").then(onFinalAttributeReadComplete, onError);
			} else {

				delete $scope.domainNames;
				delete $scope.domainAttributes;
				delete $scope.error;
				delete $scope.attributes;
				delete $scope.textfiledata;

				$scope.domainNames = response.data;
			}
		}

		var onFinalAttributeReadComplete = function(response) {
			$log.info(response.data);

			delete $scope.domainAttributes;
			delete $scope.error;
			delete $scope.attributes;
			delete $scope.textfiledata;

			$scope.domainAttributes = response.data;
		}

		$scope.getAttributes = function() {
			$log.info("Reading names File for Domain " + $scope.domainid
					+ " Type " + $scope.domaintypeid);
			// $http.post(url + "/getAttributes", {domain :
			// $scope.domainid,domaintype : $scope.domaintypeid,domainname :
			// $scope.domainnameid}).then(onAttributeReadComplete);
			$http.post(url + "/domain/"+$scope.domainid+"/type/"+$scope.domaintypeid+"/name/"+$scope.domainnameid+"/getAttributes").then(onAttributeReadComplete);

		}
		var onAttributeReadComplete = function(response) {
			$log.info(response.data);

			delete $scope.error;
			delete $scope.attributes;
			delete $scope.textfiledata;

			$scope.domainAttributes = response.data;
		}

		$scope.getAv = function() {
			$log.info("Reading values File for Domain " + $scope.domainid
					+ " Type " + $scope.domaintypeid + " name "
					+ $scope.domainnameid + " Attribute "
					+ $scope.domainAttributesid);
			$http.post(url + "/domain/"+$scope.domainid+"/type/"+$scope.domaintypeid+"/name/"+$scope.domainnameid+"/attribute/"+ $scope.domainAttributesid+"/getValue").then(onValueReadComplete, onError);
		}
		var onValueReadComplete = function(response) {
			$log.info(response.data);
			$scope.attributevalue = response.data;
		}

		

	};
	app.controller("springbootappcontroller", homectrl);

}())