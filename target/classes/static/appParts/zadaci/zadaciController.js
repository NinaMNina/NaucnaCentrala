(function () {
    'use strict';

    angular
		.module('app')
		.controller('zadaciController', zadaciController);

    zadaciController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce'];
        function zadaciController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce) {
        	var zc=this;
            $scope.token = $stateParams.token;
            $scope.sviZadaci = true;
        	$scope.jedanAZadatak = false;
        	$scope.jedanUZadatak = false;
        	$scope.theZadatak = {};
        	$scope.poruka = "";
        	$scope.pdfFajl = "";
            $scope.init = function(){
            	if($stateParams.token=="" || $stateParams.token=="{token}")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            	else{
            		$http({
                        method: 'GET',
                        url: 'https://localhost:8087/NaucnaCentrala/login/checkValidity/'+$stateParams.token
                      }).then(function successCallback(response){
                    	  if(response.data==false)
                      		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
                    	  else{
                    		  $http({
                                  method: 'GET',
                                  url: 'https://localhost:8087/NaucnaCentrala/udd/zadaci/'+$stateParams.token
                                }).then(function successCallback(response){
                              	  if(response.data!=null || response.data!=undefined)
                              		  $scope.zadaci = response.data;
                                },
                                  function errorCallback(response){
                                      alert("Greska u zahtevu");
                                  });
                    	  }
                      },
                        function errorCallback(response){
                            alert("Greska u zahtevu");
                        });
            	}
            }
            $scope.home = function(){
            	var token = $window.localStorage.getItem('token');
            	$location.path('/home/'+token);
            }
            $scope.resiZadatak = function(zad){
            	$scope.sviZadaci = false;
            	if(zad.tip=="DODAJ_PUTANJU"){
                	$scope.jedanAZadatak = true;
                	$scope.jedanUZadatak = false;            		
            	}
            	else{
                	var data = {
                			"opis": zad.opis,
                			"rad": zad.rad,
                			"tip": zad.tip            	
                	};
            		$http({
                        method: 'POST',
                        url: 'https://localhost:8087/NaucnaCentrala/udd/rad/'+$stateParams.token,
                        data: data
                      }).then(function successCallback(response){
                    	  if(response.data!=null || response.data!=undefined)
                          	$scope.theZadatak = response.data;
                      },
                        function errorCallback(response){
                            alert("Greska u zahtevu");
                        });
            		$http({
                        method: 'POST',
                        url: 'https://localhost:8087/NaucnaCentrala/udd/recenzenti/'+$stateParams.token,
                        data: data
                      }).then(function successCallback(response){
                    	  if(response.data!=null || response.data!=undefined)
                          	$scope.recenzenti = response.data;
                      },
                        function errorCallback(response){
                            alert("Greska u zahtevu");
                        });
                	$scope.jedanUZadatak = true;
                	$scope.jedanAZadatak = false;
            	}
            }
            $scope.odjaviSe = function(){
            	$stateParams.token = "";
            	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            }
            $scope.zavrsi = function(){
            	if($scope.pdfFajl==undefined || $scope.pdfFajl=="")
            		return;
            	var file = $scope.pdfFajl;
	        	var fileFormData = new FormData();
	            fileFormData.append('file', file);
	        	$http({
	        		method: 'POST',
	                url: 'https://localhost:8087/NaucnaCentrala/udd/upload/'+$stateParams.token,
	                transformRequest: angular.identity,
	                headers: {'Content-Type': undefined},
	                data: fileFormData
	        	}).then(function successCallback(response){
	        		var lokacija = response.data;
	        		alert(lokacija);
	        		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home/'+$stateParams.token;
                },
                  function errorCallback(response){
                      $scope.poruka = "Problem pri obavljanju zadatka. Neuspešno obavljeno!"
                  });
            }
        }
})();