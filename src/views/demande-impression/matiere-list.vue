<template>
  <div class="app-container">
    <h1 style="text-align: center">List des Matieres</h1>
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
          <span class="link-type">{{ row.label }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Type" width="110px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.typeMatiere }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Actions"
        align="center"
        width="230"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{ row, $index }">
          <el-button
            type="primary"
            icon="el-icon-printer"
            size="mini"
            @click="hundleImpression(row)"
            >Demmande d'impression</el-button
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
    getList() {
      this.listLoading = true;
      HTTP.get("enseignant-service/enseignants/matiere-list/1").then(
        (response) => {
          this.list = response.data;
          this.total = response.data.length;
          this.listLoading = false;
        }
      );
    },
    hundleImpression(row){
      this.$router.push({ name: 'DemandeImpress', params: {matiere: row }})
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
