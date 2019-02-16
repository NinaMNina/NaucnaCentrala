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
        	var taskId;
        	$scope.naucneOblasti = [];
        	$scope.secretMessage = "";
        	var odabraniZadatakTaskId = "";
        	var zad = {};
            $scope.init = function(){
            	if($stateParams.token=="" || $stateParams.token=="{token}" || $stateParams.token==undefined || $stateParams.token==null || $stateParams.token=="null")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            	$http({
	        		method: 'GET',
	                url: 'https://localhost:8087/NaucnaCentrala/casopis/naucneOblasti/'+$stateParams.token
	        	}).then(function successCallback(response){
	        		$scope.naucneOblasti = response.data;
                },
                  function errorCallback(response){
                      alert("Problem pri dobavljanju naucnih oblasti. Neuspešno obavljeno!")
                  });
          /*  	else{
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
            	}*/
            	{
            		$http({
                        method: 'GET',
                        url: 'https://localhost:8087/NaucnaCentrala/zadaci/get/'+$stateParams.token
                      }).then(function successCallback(response){
                    	  if(response.data!=null || response.data!=undefined)
                    		  $scope.zadaci = response.data;
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
     /*       $scope.resiZadatak = function(zadatak){
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
            }*/
            $scope.resiZadatak = function(zadatak){
            	$scope.jedanAZadatak = true;
            	$scope.jedanUZadatak = false;
            	$scope.sviZadaci = false;
            	odabraniZadatakTaskId = zadatak.taskId;
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/redirekt/'+zadatak.taskId
                  }).then(function successCallback(response){
                	  if(response.data!=""){
                		  if(response.data.redirekcija!="ostani"){
                    		  $window.localStorage.setItem('taskIdOdZadaciObrisiOdmah', response.data.taskId);
                    		  $window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/'+response.data.redirekcija+'/'+$window.localStorage.getItem('token')                			  
                		  }         		  
                	  }
                  },
                    function errorCallback(response){
                        alert("Greška pri dobavljanju zadatka");
                    	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
                    });
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/casopis/getForma/'+zadatak.taskId
                  }).then(function successCallback(response){
                	  if(response.data!="")
                		  $scope.formFields = response.data.formFields;
                	  	  taskId = response.data.taskId;
                  },
                    function errorCallback(response){
                        alert("Greska");
                    });
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
            	$window.localStorage.clear();
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
            $scope.zavrsi = function(){
            	for(var i=0; i<$scope.formFields.length; i++){
            		var ff=$scope.formFields[i];
            		if(!ff.value.value && ff.id!="up_odgovorniUrednikNO"){
                		$scope.secretMessage="Potrebno je uneti sve podatke...";
                		return;
                	}
            	}
            	var forma = [];
            	for(var i=0; i<$scope.formFields.length; i++){
            		var f0 ={};
            		f0 = {
            			"key": $scope.formFields[i].id,
            			"value": $scope.formFields[i].value.value,
            			"tip": $scope.formFields[i].type.name.toUpperCase()
            		};
            		forma.push(f0);
            	}
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/casopis/submitPodaci/'+odabraniZadatakTaskId+'/'+$stateParams.token,
                    data: forma
                  }).then(function successCallback(response){
                	  if(response.data!=null || response.data!=undefined)
                		  alert("Zadatak uspešno izvršen");
                		  $window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
                  },
                    function errorCallback(response){
            		  alert("Greška");
                  });
            }
            //UPLOAD RADA
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
	        		for(var i=0; i<$scope.formFields.length; i++)
	        			if($scope.formFields[i].id=='up_pdf')
	        				$scope.formFields[i].value.value = response.data;
                },
                  function errorCallback(response){
                      alert("Problem pri uploadu rada. Neuspešno obavljeno!")
                  });

            };
        }
})();