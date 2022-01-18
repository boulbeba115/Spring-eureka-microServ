<template>
  <div class="app-container">
    <h1 style="text-align: center">Mes Demandes d'impression</h1>
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%"
      @sort-change="sortChange"
    >
      <el-table-column
        label="ID"
        prop="id"
        sortable="custom"
        align="center"
        width="80"
        :class-name="getSortClass('id')"
      >
        <template slot-scope="{ row }">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Nom Matiere" min-width="150px">
        <template slot-scope="{ row }">
          <span class="link-type">{{ row.matiere.label }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Type" width="110px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.matiere.typeMatiere }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Date Arrivage" min-width="150px" align="center">
        <template slot-scope="{ row }">
          <span class="link-type">{{ row.dateArrivage }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Temp Arrivage" min-width="150px" align="center">
        <template slot-scope="{ row }">
          <span class="link-type">{{ row.tempArrivage }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Nombre de copie" min-width="150px" align="center">
        <template slot-scope="{ row }">
          <span class="link-type">{{ row.nbCopie }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Document" min-width="150px" align="center">
        <template slot-scope="{ row }">
          <a @click="loadPdf(row.ficherPdf)"
            ><i class="el-icon-tickets"></i>
          </a>
        </template>
      </el-table-column>
      <el-table-column label="Etat du Demande" min-width="150px" align="center">
        <template slot-scope="{ row }">
          <el-button
            v-if="row.etatDemande == 'PENDING'"
            type="warning"
            size="mini"
            >En Attente</el-button
          >
          <el-button
            v-if="row.etatDemande == 'CONFIRMED'"
            type="primary"
            size="mini"
            >Confirmer</el-button
          >
          <el-button
            v-if="row.etatDemande == 'CANCELED'"
            type="danger"
            size="mini"
            >Annuler</el-button
          >
          <el-button
            v-if="row.etatDemande == 'PRINTED'"
            type="success"
            size="mini"
            >Imprimer</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />
  </div>
</template>

<script>
import HTTP from "@/configs/axios";
import waves from "@/directive/waves"; // waves directive
import Pagination from "@/components/Pagination"; // secondary package based on el-pagination

export default {
  name: "MatiereList",
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 5,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: "+id",
      },
      sortOptions: [
        { label: "ID Ascending", key: "+id" },
        { label: "ID Descending", key: "-id" },
      ],
      showReviewer: false,
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "Edit",
        create: "Create",
      },
      dialogPvVisible: false,
      pvData: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    loadPdf(path) {
      HTTP.get(`/impression-service/demandes/pdf/${path}`).then((response) => {
        console.log(response.data);
      });
    },
    getList() {
      this.listLoading = true;
      HTTP.get(`/impression-service/demandes/enseignant/list/${1}`).then(
        (response) => {
          this.list = response.data;
          this.total = response.data.length;
          this.listLoading = false;
        }
      );
    },
    hundleImpression(row) {
      this.$router.push({ name: "DemandeImpress", params: { matiere: row } });
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },

    sortChange(data) {
      const { prop, order } = data;
      if (prop === "id") {
        this.sortByID(order);
      }
    },
    sortByID(order) {
      if (order === "ascending") {
        this.listQuery.sort = "+id";
      } else {
        this.listQuery.sort = "-id";
      }
      this.handleFilter();
    },
    getSortClass: function (key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}` ? "ascending" : "descending";
    },
  },
};
</script>
