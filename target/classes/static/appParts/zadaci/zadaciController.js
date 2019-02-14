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
            	var data = {
            			"opis": zad.opis,
            			"rad": zad.rad,
            			"tip": zad.tip            	
            	};
        		$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/udd/recenzentiSlicni/'+$stateParams.token,
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
                      	$scope.recenzenti = response.data;
                  },
                    function errorCallback(response){
                        alert("Greska u zahtevu");
                    });
            	$scope.jedanUZadatak = true;
            	$scope.jedanAZadatak = false;
            }
            
            //UPLOAD RADA
            $scope.uploadFile = function(files) {
                var fd = new FormData();
                //Take the first selected file
                fd.append("file", files[0]);
                $http({
	        		method: 'POST',
	                url: 'https://localhost:8087/NaucnaCentrala/udd/upload/'+zad.rad+'/'+$stateParams.token,
	                transformRequest: angular.identity,
	                headers: {'Content-Type': undefined},
	                data: fd
	        	}).then(function successCallback(response){
	        		
	        		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
                },
                  function errorCallback(response){
                      $scope.poruka = "Problem pri obavljanju zadatka. Neuspešno obavljeno!"
                  });

            };
        }
})();