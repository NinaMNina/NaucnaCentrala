(function () {
    'use strict';

    angular
		.module('app')
		.controller('pregledIzmenaController', pregledIzmenaController);

    pregledIzmenaController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce'];
        function pregledIzmenaController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce) {
            $scope.token = $stateParams.token;
        	$scope.porukica = "";
        	$scope.secretMessage = "";
            $scope.init = function(){
            	if($stateParams.token=="" || $stateParams.token=="{token}" || $stateParams.token==undefined || $stateParams.token==null || $stateParams.token=="null")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            	$http({
	        		method: 'GET',
	                url: 'https://localhost:8087/NaucnaCentrala/zadaci/pregledIzmena/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')
	        	}).then(function successCallback(response){
	        		$scope.ocene = response.data.ocene;
	        		$scope.formFields = response.data.podaci;
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
            $scope.otvoriRad = function(){       
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/pregledPDFa/getPDF/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')
                  }).then(function successCallback(response){
                	  if(response!=null){
                		  var anchor = angular.element('<a/>');
                          anchor.attr({
                              href: 'data:attachment/pdf;charset=utf-8,' + encodeURI(response.data),
                              target: '_blank',
                              download: 'rad.pdf'
                          })[0].click();
                  	  }
                  },
                    function errorCallback(response){
                        
                    });            	
            }
            $scope.zavrsi = function(isZadovoljan){                		           	 	                   	
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/pregledIzmena/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')+'/'+isZadovoljan
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
	        		for(var i=0; i<$scope.formFields.length; i++)
	        			if($scope.formFields[i].key=='up_pdf')
	        				$scope.formFields[i].value = response.data;
                },
                  function errorCallback(response){
                      alert("Problem pri uploadu rada. Neuspešno obavljeno!")
                  });

            };
        }
})();