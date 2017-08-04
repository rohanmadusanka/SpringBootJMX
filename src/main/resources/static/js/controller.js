(function() {

	var app = angular.module("springbootapp", []);
	var homectrl = function($scope, $http, $interval, $log) {

		var url = "http://localhost:8080";
		$scope.hint="";
		var onError = function(reason) {
			$scope.error = "Oops.. Something went wrong...."+reason;
		}

		/* Read Text File Start */

		$scope.readTextFile = function() {
			$log.info("Reading Domain File");
			$http.get(url + "/readText").then(onreadTextComplete, onError);
		}
		var onreadTextComplete = function(response) {
			$log.info(response);

			delete $scope.hint;
			delete $scope.domains;
			delete $scope.domainTypes;
			delete $scope.domainNames;
			delete $scope.domainAttributes;
			delete $scope.success;
			delete $scope.error;
			delete $scope.textfiledata;
			delete $scope.attributevalue;

			if (response.data.length === 0) {
				$log.info("Text File is empty");
				$scope.error = "Sorry. Text file is empty.";
			} else {
				$scope.textfiledata = response.data;
			}
		}
		/* Read Text File End */

		/* Get all domains Start */
		$scope.getDomains = function() {
			$log.info("Reading Domain File");
			$http.get(url + "/getDomains").then(onDomainReadComplete, onError);
		}
		var onDomainReadComplete = function(response) {
			$log.info(response.data);

			delete $scope.hint;
			delete $scope.domains;
			delete $scope.domainTypes;
			delete $scope.domainNames;
			delete $scope.domainAttributes;
			delete $scope.success;
			delete $scope.error;
			delete $scope.textfiledata;

			$scope.domains = response.data;
		}
		/* Get all domains End */

		/* Get all types according to domain start */
		$scope.getTypes = function() {
			$log.info("Reading Type File for Domain " + $scope.domainid);
			$http.post(url + "/domain/" + $scope.domainid + "/getTypes").then(
					onTypeReadComplete, onError);
		}

		var onTypeReadComplete = function(response) {
			$log.info(response.data);

			delete $scope.hint;
			delete $scope.domainTypes;
			delete $scope.domainNames;
			delete $scope.domainAttributes;
			delete $scope.success;
			delete $scope.error;
			delete $scope.textfiledata;

			$scope.domainTypes = response.data;
		}
		/* Get all types according to domain end */

		/* Get all name according to domain and type start */
		$scope.getNames = function() {
			$log.info("Reading names File for Domain " + $scope.domainid
					+ " Type " + $scope.domaintypeid);
			$http.post(
					url + "/domain/" + $scope.domainid + "/type/"
							+ $scope.domaintypeid + "/getNames").then(
					onNameReadComplete, onError);
		}
		var onNameReadComplete = function(response) {
			$log.info(response.data);
			$log.info("length=" + response.data[0]);
			if (response.data[0] === null) {
				$log.info("No names declared...");
				$http.post(
						url + "/domain/" + $scope.domainid + "/type/"
								+ $scope.domaintypeid
								+ "/name/null/getAttributes").then(
						onFinalAttributeReadComplete, onError);
			} else {

				delete $scope.hint;
				delete $scope.domainNames;
				delete $scope.domainAttributes;
				delete $scope.success;
				delete $scope.error;
				delete $scope.textfiledata;

				$scope.domainNames = response.data;
			}
		}
		/* Get all name according to domain and type end */

		var onFinalAttributeReadComplete = function(response) {
			$log.info(response.data);

			delete $scope.hint;
			delete $scope.domainAttributes;
			delete $scope.success;
			delete $scope.error;
			delete $scope.textfiledata;

			$scope.domainAttributes = response.data;
		}

		/* Get all attributes according to domain , type and name start */
		$scope.getAttributes = function() {
			$log.info("Reading names File for Domain " + $scope.domainid
					+ " Type " + $scope.domaintypeid);
			$http.post(
					url + "/domain/" + $scope.domainid + "/type/"
							+ $scope.domaintypeid + "/name/"
							+ $scope.domainnameid + "/getAttributes").then(
					onAttributeReadComplete);

		}
		var onAttributeReadComplete = function(response) {
			$log.info(response.data);

			delete $scope.hint;
			delete $scope.error;
			delete $scope.success;
			delete $scope.textfiledata;

			$scope.domainAttributes = response.data;
		}
		/* Get all attributes according to domain , type and name end */

		/* Get value according to domain , type , name and attribute start */
		$scope.getAv = function() {
			$log.info("Reading values File for Domain " + $scope.domainid
					+ " Type " + $scope.domaintypeid + " name "
					+ $scope.domainnameid + " Attribute "
					+ $scope.domainAttributesid);
			$http.post(
					url + "/domain/" + $scope.domainid + "/type/"
							+ $scope.domaintypeid + "/name/"
							+ $scope.domainnameid + "/attribute/"
							+ $scope.domainAttributesid + "/getValue").then(
					onValueReadComplete, onError);
		}
		var onValueReadComplete = function(response) {
			$log.info(response.data);
			$scope.attributevalue = response.data;
		}
		/* Get value according to domain , type , name and attribute start */

		/* Fill table start */

		$scope.fillTable = function() {
			$log.info("Reading Domain File");
			//$http.get(url + "/fillTable").then(onfillTableComplete, onError);
			
			$http({
				url : url + '/fillTable',
				method : 'GET',
				transformResponse : [ function(data) {
					$log.info(data);

					delete $scope.hint;
					delete $scope.success;
					delete $scope.error;
					
					if(data==="success"){
						$scope.success="File updated successfully....";
					}else{
						$scope.error="Oops.. Something went wrong....";
					}
				} ]
			});
			
		}
		/*var onfillTableComplete = function(response) {
			$log.info(response);

		}*/
		/* Fill table end */

		/* Open file start */
		$scope.error="";
		$scope.openTextFile = function() {
			$log.info("Opening Text File");
			//$http.get(url + "/openFile").then(onopenFileComplete, onError);
			
			
			
			
			
			$http({
				url : url + '/openFile',
				method : 'GET',
				transformResponse : [ function(data) {
					$log.info(data);

					delete $scope.success;
					delete $scope.error;
					delete $scope.hint;
					
					if(data==="success"){
						$scope.success="File successfully opened....";
					}else{
						$scope.error="Oops.. Something went wrong....";
					}
				} ]
			});
			
		}
	/*	var onopenFileComplete = function(response) {
			$log.info(response);

		}*/
		/* Open file end */
		
		
		
		$scope.getValuesToNewPage = function() {
			
			window.location = "/getJMXValues";
		}
		
		
		
		
		
		
		
	};
	app.controller("springbootappcontroller", homectrl);

}())