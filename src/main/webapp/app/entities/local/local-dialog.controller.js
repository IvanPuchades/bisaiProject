(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('LocalDialogController', LocalDialogController);

    LocalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Local', 'Torneo'];

    function LocalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Local, Torneo) {
        var vm = this;

        vm.local = entity;
        vm.clear = clear;
        vm.save = save;
        vm.torneos = Torneo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.local.id !== null) {
                Local.update(vm.local, onSaveSuccess, onSaveError);
            } else {
                Local.save(vm.local, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bisaiApp:localUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
