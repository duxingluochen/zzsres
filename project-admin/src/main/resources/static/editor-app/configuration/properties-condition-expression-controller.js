/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

/*
 * Condition expression
 */

var KisBpmConditionExpressionCtrl = [ '$scope', '$modal', function($scope, $modal) {

    // Config for the modal window
    var opts = {
        template:  'editor-app/configuration/properties/condition-expression-popup.html?version=' + Date.now(),
        scope: $scope
    };

    // Open the dialog
    $modal(opts);
}];

var KisBpmConditionExpressionPopupCtrl = [ '$scope', '$translate', '$http', function($scope, $translate, $http) {
	var options = null;
	//访问后台
	$scope.getAllRoles = function (successCallback) {
		//debugger;
	    $http({    
	    	method: 'get',
	        headers: {'Accept': 'application/json',
	                  'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
	        	url:'view/findEntryDataById.do?id='+theRequest.ActivitiModelRelesId,
	        	})
	        .success(function (data, status, headers, config) {
	        	debugger;
	        	var obj = data.message;
	        	$scope.property.options = JSON.parse(obj);
	        })
	        .error(function (data, status, headers, config) {
	        });
	};
	$scope.getAllRoles(function(){});
//	$scope.property.options = JSON.parse(options);
	$scope.property.conditions = JSON.parse( '[{"name":"大于","value":"1"},'
			+'{"name":"小于","value":"2"},'
			+'{"name":"等于","value":"3"},'
			+'{"name":"不等于","value":"4"},'
			+'{"name":"大于等于","value":"5"},'
			+'{"name":"小于等于","value":"6"} '
			+']');
	// Put json representing condition on scope
    if ($scope.property.value !== undefined && $scope.property.value !== null) {
    	//debugger;
        $scope.conditionExpression = {value: $scope.property.value};
    } else {
        $scope.conditionExpression = {value: ''};
    }
    
    $scope.multiInstanceChanged = function() {
    	$scope.updatePropertyInModel($scope.property);
    };
    $scope.conditionChanged = function() {
    	$scope.updatePropertyInModel($scope.property);
    };
    
    $scope.save = function() {
    	var sas = $scope.condition.value;//条件
    	var sas1 = $scope.multiInstance.value;
    	var value = $scope.conditionExpression.value;
    	//debugger;
    	if(value === "" || value ==null){ // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
    		value = value;
        }else{
        	if(!isNaN(value)){
        		value = value;
            }else{
            	value= '\"'+value+'\"';
            }
        }
    	   	
    	var result = null;
    	if(1==sas){//大于
    		result="${"+sas1+">"+value+"}";
    	}else if (2==sas) {//小于
    		result="${"+sas1+"<"+value+"}";
		}else if (3==sas) {//等于
			result="${"+sas1+"=="+value+"}";
		}else if (4==sas) {//不等于
			result="${"+sas1+"!="+value+"}";
		}else if (5==sas) {//大于等于
			result="${"+sas1+">="+value+"}";
		}else if (6==sas) {//小于等于
			result="${"+sas1+"<="+value+"}";
		}
        $scope.property.value = result;
        $scope.updatePropertyInModel($scope.property);
        $scope.close();
    };

    // Close button handler
    $scope.close = function() {
    	$scope.property.mode = 'read';
    	$scope.$hide();
    };
}];