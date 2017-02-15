(function() {
    'use strict';
    angular
        .module('bisaiApp')
        .factory('Clasificacion', Clasificacion);

    Clasificacion.$inject = ['$resource'];

    function Clasificacion ($resource) {
        var resourceUrl =  'api/clasificacions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
