(function () {
    'use strict';

    angular
		.module('app')
		.controller('konacniUploadController', konacniUploadController);

    konacniUploadController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce'];
        function konacniUploadController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce) {
        	$scope.odluka = {};
        	$scope.podaci = {};
        	var novaPutanja = "";
        	$scope.komentar = "";
        	$scope.formField = {"key": "up_pdf", "value": "", "tip": "STRING"};
            $scope.init = function(){
            	 
            }
            $scope.home = function(){
            	$window.localStorage.remove('taskIdOdZadaciObrisiOdmah');
            	$location.path('/home');
            }
    
            $scope.zavrsi = function(){    
            	if($scope.formField.value==""){
            		alert("Niste upload-ovali pdf");
            		return;            		
            	}            
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/konacniUpload/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah'),
                    data: $scope.formField
                  }).then(function successCallback(response){
                	  if(response.data!=""){
                    	$window.localStorage.removeItem('taskIdOdZadaciObrisiOdmah');
                		alert("Cestitamo! Proces je zavrsen");
                    	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home'             			          		  
                	  }
                  },function errorCallback(response){
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
	        		$scope.formField.value = response.data;
                },
                  function errorCallback(response){
                      alert("Problem pri uploadu rada. Neuspešno obavljeno!")
                  });

            };
            
            $scope.odjaviSe = function(){
            	$stateParams.token = "";
            	$window.localStorage.clear();
            	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
            }
        }
})();