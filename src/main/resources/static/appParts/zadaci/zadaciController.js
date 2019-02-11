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
        	var odabrani = [];
        	$scope.poruka = "";
        	$scope.pdfFajl = "";
        	$scope.porukica = "";
        	var zad = {};
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
            $scope.resiZadatak = function(zadatak){
            	zad = zadatak;
            	$scope.sviZadaci = false;
            	if(zadatak.tip=="DODAJ_PUTANJU"){
                	$scope.jedanAZadatak = true;
                	$scope.jedanUZadatak = false;            		
            	}
            	else{
                	var data = {
                			"opis": zadatak.opis,
                			"rad": zadatak.rad,
                			"tip": zadatak.tip            	
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
            $scope.menjaStanje = function(id){
            	if(odabrani.includes(id)){
            		var ind = odabrani.indexOf(id);
            		odabrani.splice(ind, 1);
            		return;
            	}
            	odabrani.push(id);
            }
            $scope.zavrsiUrednik = function(){
            	if(odabrani.length<2){
            		$scope.porukica = "Moraju biti odabrana najmanje 2 recenzenta";
            		return;
            	}
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/udd/dodajRecenzente/'+$scope.theZadatak.id+'/'+$stateParams.token,
                    data: odabrani
                  }).then(function successCallback(response){
                	  if(response.data!=null || response.data!=undefined)
                      	$scope.recenzenti = response.data;
                	  	$scope.jedanUZadatak = false;
                	  	$scope.sviZadaci = true;
                	  	odabrani = [];
                	  	$scope.poruka = "";
                	  	$scope.porukica = "";
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
                  },
                    function errorCallback(response){
                        alert("Greska u zahtevu");
                    });
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
            
            //FILTERI
            $scope.filterStrucni = function(){
            	var data = {
            			"opis": zad.opis,
            			"rad": zad.rad,
            			"tip": zad.tip            	
            	};
        		$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/udd/recenzentiStrucni/'+$stateParams.token,
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
            $scope.filterBez = function(){
            	var data = {
            			"opis": zad.opis,
            			"rad": zad.rad,
            			"tip": zad.tip            	
            	};
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
            
            $scope.filterSlicni = function(){
            	
            }
            $scope.filterUdaljeni = function(){
            	var data = {
            			"opis": zad.opis,
            			"rad": zad.rad,
            			"tip": zad.tip            	
            	};
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/udd/recenzentiUdaljeni/'+$stateParams.token,
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
                    url: 'https://localhost:8087/NaucnaCentrala/udd/recenzentiStrucni/'+$stateParams.token,
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
})();