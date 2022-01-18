<template>
  <div>
    <h1 style="text-align: center">
      Demande d'impression pour la matiere {{ matiere.label }}
    </h1>
    <el-card class="box-card" style="padding: 10px; margin: 20px">
      <el-form
        :model="demandeImpressionForm"
        :rules="rules"
        ref="demandeImpressionForm"
        label-width="200px"
        class="demo-demandeImpressionForm"
      >
        <el-form-item label="Temps Arrivage" required>
          <el-col :span="11">
            <el-form-item prop="dateArrivage">
              <el-date-picker
                type="date"
                placeholder="Choisissez une date"
                v-model="demandeImpressionForm.dateArrivage"
                style="width: 100%"
                :picker-options="pickerOptions"
                required
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="tempArrivage">
              <el-time-picker
                placeholder="Choisissez une heure"
                v-model="demandeImpressionForm.tempArrivage"
                style="width: 100%"
              ></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="Description" prop="description">
          <el-input
            type="textarea"
            v-model="demandeImpressionForm.description"
          ></el-input>
        </el-form-item>
        <el-form-item label="Nombre de copies" prop="nbCopie" required>
          <el-input-number
            style="width: 358px"
            v-model="demandeImpressionForm.nbCopie"
            :step="1"
            :min="0"
            :max="nbMaxCp"
          ></el-input-number>
        </el-form-item>
        <el-form-item>
          <div>
            <input type="file" @change.prevent="uploadFile" ref="file" />
          </div>
        </el-form-item>
        <el-form-item style="text-align: right">
          <el-button type="primary" @click="submitDemande()">Create</el-button>
          <el-button @click="resetForm('demandeImpressionForm')"
            >Reset</el-button
          >
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import HTTP from "@/configs/axios";
export default {
  props: {
    matiere: Object,
  },
  data() {
    return {
      demandeImpressionForm: {
        dateArrivage: "",
        description: "",
        tempArrivage: "",
        nbCopie: "",
        ficherPdf: "",
        matiere: null,
      },
      nbMaxCp: 0,
      Images: null,
      rules: {
        nbCopie: [
          {
            required: true,
            message: "Veuillez saisir le nombre de copies",
            trigger: "blur",
          },
        ],
        dateArrivage: [
          {
            type: "date",
            required: true,
            message: "Veuillez choisir une date",
            trigger: "change",
          },
        ],
        tempArrivage: [
          {
            type: "date",
            required: true,
            message: "Veuillez choisir une heure",
            trigger: "change",
          },
        ],
        description: [
          {
            required: true,
            message: "Veuillez entrer la description",
            trigger: "blur",
          },
        ],
      },
      pickerOptions: {
        disabledDate(date) {
          return date < Date.now();
        },
      },
    };
  },
  created() {
    this.getMax();
  },
  methods: {
    getMax() {
      HTTP.get(
        `/enseignant-service/enseignants/matiere-groupes/${1}/${
          this.matiere.id
        }`
      ).then((res) => {
        if (res.data != null && res.data.length > 0) {
          this.nbMaxCp = res.data.reduce((a, b) => {
            return (a += b.nbEtudiant);
          }, 0);
        }
      });
    },
    uploadFile(event) {
      this.Images = this.$refs.file.files[0];
      this.submitFile(event);
      event.preventDefault();
    },
    submitFile(event) {
      const formData = new FormData();
      formData.append("file", this.Images);
      const headers = { "Content-Type": "multipart/form-data" };
      HTTP.post("impression-service/demandes/document", formData, {
        headers,
      })
        .then((res) => {
          this.demandeImpressionForm.ficherPdf = res.data;
          event.preventDefault();
        })
        .catch((err) => {
          this.$notify.error({
            title: "Error",
            message: err.response.data,
          });
        });
    },
    submitForm(formName) {
      if (this.demandeImpressionForm.ficherPdf == "") {
        this.$notify.error({
          title: "Error",
          message: "Pas de document attaché",
        });
        return;
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.submitDemande();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    submitDemande() {
      const data = {
        idEnseignant: 1,
        idMatiere: this.matiere.id,
        nbCopie: 20,
        dateArrivage: this.demandeImpressionForm.dateArrivage,
        tempArrivage: this.demandeImpressionForm.tempArrivage,
        description: this.demandeImpressionForm.description,
        ficherPdf: this.demandeImpressionForm.ficherPdf,
      };
      const headers = { "Content-Type": "application/json" };
      HTTP.post("/impression-service/demandes", data, { headers }).then(
        (res) => {
          this.$router.push({name:"MesDemandes"});
        }
      );
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style>
</style>