(function() {
    'use strict';
    angular
        .module('bisaiApp')
        .factory('Juego', Juego);

    Juego.$inject = ['$resource'];

    function Juego ($resource) {
        var resourceUrl =  'api/juegos/:id';

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
