(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('LocalController', LocalController);

    LocalController.$inject = ['$scope', '$state', 'Local'];

    function LocalController ($scope, $state, Local) {
        var vm = this;

        vm.locals = [];

        loadAll();

        function loadAll() {
            Local.query(function(result) {
                vm.locals = result;
                vm.searchQuery = null;
            });
        }
    }
})();
