(function () {
    'use strict';

    angular
		.module('app')
		.controller('doradaRadaController', doradaRadaController);

    doradaRadaController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce'];
        function doradaRadaController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce) {
            $scope.token = $stateParams.token;
        	$scope.porukica = "";
        	$scope.secretMessage = "";
        	$scope.formFields = {"key": "up_pdf", "value": "", "tip": "STRING"};
            $scope.init = function(){
            	if($stateParams.token=="" || $stateParams.token=="{token}" || $stateParams.token==undefined || $stateParams.token==null || $stateParams.token=="null")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            	$http({
	        		method: 'GET',
	                url: 'https://localhost:8087/NaucnaCentrala/zadaci/doradaRada/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')
	        	}).then(function successCallback(response){
	        		$scope.ocene = response.data;
                },
                  function errorCallback(response){
                  });
            }
            $scope.home = function(){
            	var token = $window.localStorage.getItem('token');
            	$location.path('/home');
            }                     
            $scope.odjaviSe = function(){
            	$stateParams.token = "";
            	$window.localStorage.clear();
            	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            }

            $scope.zavrsi = function(){        		
        		if($scope.formFields.value==""){
        			alert("Molimo, upload-ujte rad");
            		return;
        		}            	 	                   	
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/doradaRada/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah'),
                    data: $scope.formFields
                  }).then(function successCallback(response){
                	  if(response.data!=""){
                    	$window.localStorage.removeItem('taskIdOdZadaciObrisiOdmah');
                    	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home'             			          		  
                	  }
                  },
                    function errorCallback(response){
                        alert("Greška pri obavljanu zadatka");
                    	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
                    });     
            }
            $scope.uploadFile = function(files) {
                var fd = new FormData();
                //Take the first selected file
                fd.append("file", files[0]);
                $http({
	        		method: 'POST',
	                url: 'https://localhost:8087/NaucnaCentrala/casopis/proba/upload/'+$stateParams.token,
	                transformRequest: angular.identity,
	                headers: {'Content-Type': undefined},
	                data: fd
	        	}).then(function successCallback(response){
	        		$scope.formFields.value = response.data;
                },
                  function errorCallback(response){
                      alert("Problem pri uploadu rada. Neuspešno obavljeno!")
                  });

            };
        
        }
})();