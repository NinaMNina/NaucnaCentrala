(function () {
    'use strict';

    angular
		.module('app')
		.controller('uploadPDFaController', uploadPDFaController);

    uploadPDFaController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce'];
        function uploadPDFaController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce) {
        	$scope.odluka = {};
        	$scope.podaci = {};
        	var novaPutanja = "";
        	$scope.komentar = "";
        	$scope.formFields = [];
            $scope.init = function(){
            	$scope.rok.dana=0;
            	$scope.rok.minuta=0;
            	$scope.rok.sati=0;
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/uploadPDFa/komentar/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')
                  }).then(function successCallback(response){
                	  if(response.data!=""){   
                      	$scope.formFields = response.data;
                      	for(var i=0; $scope.formFields.length; i++){
                      		if($scope.formFields[i].key=="pp_komentarUradnika")
                      			$scope.komentar = $scope.formFields[i].value
                      		if($scope.formFields[i].key=="up_pdf")
                      			$scope.formFields[i].value = "";
                      	}
                	  }
                  },function errorCallback(response){
                    });     
            }
            $scope.home = function(){
            	$window.localStorage.remove('taskIdOdZadaciObrisiOdmah');
            	$location.path('/home');
            }
    
            $scope.zavrsi = function(){     
            	for(var i=0; i<$scope.formFields.length; i++){
            		var ff=$scope.formFields[i];
            		if(!ff.value && ff.key!="pp_komentarUradnika"){
                		$scope.secretMessage="Potrebno je uneti sve podatke...";
                		return;
                	}
            	}
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/uploadPDFa/reseno/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah'),
                    data: $scope.formFields
                  }).then(function successCallback(response){
                	  if(response.data!=""){
                    	$window.localStorage.removeItem('taskIdOdZadaciObrisiOdmah');
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
	        		for(var i=0; i<$scope.formFields.length; i++)
	        			if($scope.formFields[i].key=='up_pdf')
	        				$scope.formFields[i].value = response.data;
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