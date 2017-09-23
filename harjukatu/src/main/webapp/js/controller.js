var harjukatuApp = angular.module('harjukatuApp', ["ui.bootstrap"]);

harjukatuApp.controller('harjukatuCtrl', function($scope, $http) {
    $scope.showModal = true;
    $http.get("/api/menu")
    .then(function(response) {
        $scope.menu = response.data; 
    })
    .catch(function (data) {
        console.log('get menu error',data)
    });
    $http.get("/api/posts")
    .then(function(response) {
        $scope.posts = response.data; 
    })
    .catch(function (data) {
        console.log('get posts error',data)
    });
    $http.get("/api/newposts")
    .then(function(response) {
        $scope.newposts = response.data; 
    })
    .catch(function (data) {
        console.log('get posts error',data)
    });
    $scope.removeItem = function (idx) {
        var url = "/api/posts/"+idx+"/del";
        $http.get(url)
        .then(function(response) {
            $scope.posts = response.data; 
        })
        .catch(function (data) {
            console.log('del posts error',url)
        });
    }
});

// create angular controller
harjukatuApp.controller('postController', function($scope, $http) {
    $scope.showModal = true;
    $http.get('/api/menu')
    .then(function(response) {
        $scope.menu = response.data; 
    })
    .catch(function (data) {
        console.log('get menu error',data);
    });

    $scope.addPost = function() {

        console.log('scope.formError =',$scope.formError);
        
        $http.post('/api/add',$scope.post)
        .then(function(response) {
            console.log('post',response);
            $scope.message = response.data.message;
    
        })
        .catch(function (data) {
            console.log('post error',data);
        });
    };
});
