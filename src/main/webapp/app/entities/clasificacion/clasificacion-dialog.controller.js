(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('ClasificacionDialogController', ClasificacionDialogController);

    ClasificacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Clasificacion', 'Torneo', 'Equipo'];

    function ClasificacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Clasificacion, Torneo, Equipo) {
        var vm = this;

        vm.clasificacion = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.torneos = Torneo.query();
        vm.equipos = Equipo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.clasificacion.id !== null) {
                Clasificacion.update(vm.clasificacion, onSaveSuccess, onSaveError);
            } else {
                Clasificacion.save(vm.clasificacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bisaiApp:clasificacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto = function ($file, clasificacion) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        clasificacion.foto = base64Data;
                        clasificacion.fotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
