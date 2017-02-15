(function() {
    'use strict';
    angular
        .module('bisaiApp')
        .factory('Porra', Porra);

    Porra.$inject = ['$resource'];

    function Porra ($resource) {
        var resourceUrl =  'api/porras/:id';

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
