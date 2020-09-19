function contains(target, lookingFor) {
    return target && target.indexOf(lookingFor) >= 0;
}

var placaFilterParams = {
    filterOptions: ['contains', 'notContains'],
    textFormatter: function (r) {
        if (r == null) return null;

        return r
            .toLowerCase()
            .replace(/\s/g, '')
            .replace(/[àáâãäå]/g, 'a')
            .replace(/æ/g, 'ae')
            .replace(/ç/g, 'c')
            .replace(/[èéêë]/g, 'e')
            .replace(/[ìíîï]/g, 'i')
            .replace(/ñ/g, 'n')
            .replace(/[òóôõö]/g, 'o')
            .replace(/œ/g, 'oe')
            .replace(/[ùúûü]/g, 'u')
            .replace(/[ýÿ]/g, 'y')
            .replace(/\W/g, '');
    },
    debounceMs: 0,
    suppressAndOrCondition: true,
};

function ButtonRendererRenderer(params) {
}

var columnDefs = [
    {
        field: 'id'
    },
    {
        field: 'placa',
        filter: 'agTextColumnFilter',
        filterParams: placaFilterParams,
    },
    {
        field: 'tipoVeiculo',
    },
    {
        field: 'marca',
    },
    {
        field: 'modelo',
    },
    {
        field: 'possuiUtilitario',
    },
    {
        field: 'horaEntrada',
    },
    {
        field: 'id',
        cellRenderer: 'buttonRender'
    }
];

function buttonRender(params) {
    var resultElement = document.createElement("span");
    var linkElement = document.createElement("a");
    linkElement.href = "/estacionamento/detalhessaidaveiculo/" + params.value;
    linkElement.text = "Saida";
    linkElement.className = "btn btn-secondary btn-block";
    resultElement.appendChild(linkElement);
    return resultElement;
}

var gridOptions = {
    defaultColDef: {
        flex: 1,
        sortable: true,
        filter: true,
        floatingFilter: true,
    },
    columnDefs: columnDefs,
    rowData: null,
    components: {
        buttonRender: buttonRender
    }
};



// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function () {
    var gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);

    agGrid
        .simpleHttpRequest({
            url:
                'http://localhost:8080/estacionamento/dados/veiculos',
        })
        .then(function (data) {
            gridOptions.api.setRowData(data);
        });
});
