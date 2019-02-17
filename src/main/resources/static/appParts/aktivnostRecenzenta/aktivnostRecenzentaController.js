(function () {
    'use strict';

    angular
		.module('app')
		.controller('aktivnostRecenzentaController', aktivnostRecenzentaController);

    aktivnostRecenzentaController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce'];
        function aktivnostRecenzentaController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce) {
            $scope.token = $stateParams.token;
            $scope.init = function(){
            	if($stateParams.token=="" || $stateParams.token=="{token}" || $stateParams.token==undefined || $stateParams.token==null || $stateParams.token=="null")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            	$http({
	        		method: 'GET',
	                url: 'https://localhost:8087/NaucnaCentrala/zadaci/aktivnostiRecenzenta/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')
	        	}).then(function successCallback(response){
	        		$scope.formFields = response.data;
                },
                  function errorCallback(response){
                 //     alert("Problem pri dobavljanju naucnih oblasti. Neuspešno obavljeno!")
                  });
              }
            $scope.home = function(){
            	var token = $window.localStorage.getItem('token');
            	$location.path('/home/'+token);
            }
            $scope.zavrsi = function(){
            	for(var i=0; i<$scope.formFields.length; i++){
            		var ff=$scope.formFields[i];
            		if(!ff.value && ff.key=="cr__ocena"){
                		$scope.secretMessage="Obavezno je uneti ocenu...";
                		return;
                	}
            	}            	
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/aktivnostiRecenzenta/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')+'/'+$stateParams.token,
                    data: $scope.formFields
                  }).then(function successCallback(response){
                	  if(response.data!=null || response.data!=undefined)
                		  alert("Zadatak uspešno izvršen");
                		  $window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
                  },
                    function errorCallback(response){
            		  alert("Greška");
                  });
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
            
        }
})();