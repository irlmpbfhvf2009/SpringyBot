import{_ as e,d as a,J as l,r as o,N as m,a as r,o as t,c as d,w as u,b as s,j as n,g as i}from"./index.c4c68ae8.js";import{b as p,c}from"./job.268bb027.js";var f=e(a({setup(){const e=l();let a=o({userId:"",botId:"",company:"",position:"",baseSalary:"",commission:"",workTime:"",requirements:"",location:"",flightNumber:""});return m((()=>{p({ub:decodeURIComponent(e.query.ub)}).then((e=>{a.value.userId=e.data.userId,a.value.botId=e.data.botId,a.value.company=e.data.company,a.value.position=e.data.position,a.value.baseSalary=e.data.baseSalary,a.value.commission=e.data.commission,a.value.workTime=e.data.workTime,a.value.requirements=e.data.requirements,a.value.location=e.data.location,a.value.flightNumber=e.data.flightNumber}))})),{form:a}},methods:{submit(){let e=this.form;this.addForm(e)},addForm(e){c(e).then((e=>{this.$message({type:"success",message:e.msg})}))}}}),[["render",function(e,a,l,o,m,p){const c=r("el-input"),f=r("el-form-item"),b=r("el-button"),V=r("el-form");return t(),d(V,{class:"form"},{default:u((()=>[s(f,{label:"公司名称：",prop:"company"},{default:u((()=>[s(c,{modelValue:e.form.company,"onUpdate:modelValue":a[0]||(a[0]=a=>e.form.company=a),placeholder:"请输入公司名称"},null,8,["modelValue"])])),_:1}),s(f,{label:"职位名称：",prop:"position"},{default:u((()=>[s(c,{modelValue:e.form.position,"onUpdate:modelValue":a[1]||(a[1]=a=>e.form.position=a),placeholder:"请输入职位名称"},null,8,["modelValue"])])),_:1}),s(f,{label:"底薪：",prop:"baseSalary"},{default:u((()=>[s(c,{modelValue:e.form.baseSalary,"onUpdate:modelValue":a[2]||(a[2]=a=>e.form.baseSalary=a),placeholder:"请输入底薪"},null,8,["modelValue"])])),_:1}),s(f,{label:"提成：",prop:"commission"},{default:u((()=>[s(c,{modelValue:e.form.commission,"onUpdate:modelValue":a[3]||(a[3]=a=>e.form.commission=a),placeholder:"请输入提成"},null,8,["modelValue"])])),_:1}),s(f,{label:"上班时间：",prop:"workTime"},{default:u((()=>[s(c,{modelValue:e.form.workTime,"onUpdate:modelValue":a[4]||(a[4]=a=>e.form.workTime=a),placeholder:"请输入上班时间"},null,8,["modelValue"])])),_:1}),s(f,{label:"要求内容：",prop:"requirements"},{default:u((()=>[s(c,{type:"textarea",modelValue:e.form.requirements,"onUpdate:modelValue":a[5]||(a[5]=a=>e.form.requirements=a),maxlength:"50",placeholder:"限50字以内"},null,8,["modelValue"])])),_:1}),s(f,{label:"🐌地址：",prop:"location"},{default:u((()=>[s(c,{modelValue:e.form.location,"onUpdate:modelValue":a[6]||(a[6]=a=>e.form.location=a),placeholder:"请输入地址"},null,8,["modelValue"])])),_:1}),s(f,{label:"✈️咨询飞机号：",prop:"flightNumber"},{default:u((()=>[s(c,{modelValue:e.form.flightNumber,"onUpdate:modelValue":a[7]||(a[7]=a=>e.form.flightNumber=a),placeholder:"请输入咨询飞机号"},null,8,["modelValue"])])),_:1}),n("div",null,[s(b,{type:"primary",onClick:e.submit},{default:u((()=>[i("確認")])),_:1},8,["onClick"])])])),_:1})}],["__scopeId","data-v-7685c6db"]]);export{f as default};
