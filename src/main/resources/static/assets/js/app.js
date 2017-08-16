/**
 * Created by 502662077 on 8/15/17.
 */
(function() {
    var app = angular.module('store', []);

    app.controller('StoreController', [ '$http', function($http){
        var store = this;
        store.products = [];
        $http.get('/assets/includes/store-products.json')
            .then(function(response){
                store.products = response.data;
                // console.log(store.products);
            }, function(error) { });
    } ]);

})();