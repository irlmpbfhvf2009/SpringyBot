import{T as e}from"./index.d30c5476.js";import{b as l,u as a,g as t,d as o,s as n,a as i}from"./bot.e9cd3279.js";import{d as r,L as d,r as s,_ as u,a as m,o as c,c as f,w as p,b as h,e as g,F as b,f as v,g as y,t as _,h as V,i as S,j as C,k as D,l as w,m as k}from"./index.c4c68ae8.js";const T=[{value:1,label:"运动"},{value:2,label:"健身"},{value:3,label:"跑酷"},{value:4,label:"街舞"}],F=[{value:!0,label:"开启"},{value:!1,label:"关闭"}],x=r({components:{Layer:d},props:{layer:{type:Object,default:()=>({show:!1,title:"",showButton:!0})}},setup(e,l){const a=s(null),t=s(null);let o=s({token:"5855785269:AAH9bvPpYudd2wSAvMnBTiKakCeoB92_Z_8",username:"CCP_1121_BOT",state:!1,config:{inviteFriendsSet:!1,followChannelSet:!1,invitationBonusSet:!1,deleteSeconds:6,inviteFriendsAutoClearTime:3,inviteFriendsQuantity:2,inviteMembers:6,inviteEarnedOutstand:1.2,contactPerson:"@aa"}});return e.layer.row&&(o.value=JSON.parse(JSON.stringify(e.layer.row))),{form:o,layerDom:t,ruleForm:a,selectData:T,radioData:F}},methods:{submit(){this.ruleForm&&this.ruleForm.validate((e=>{if(!e)return!1;{let e=this.form;this.layer.row?this.updateForm(e):this.addForm(e)}}))},addForm(e){l(e).then((e=>{this.$message({type:"success",message:"新增成功"}),this.$emit("getTableData",!0),this.layerDom&&this.layerDom.close()}))},updateForm(e){a(e).then((e=>{this.$message({type:"success",message:e.msg}),this.$emit("getTableData",!1),this.layerDom&&this.layerDom.close()}))}}});const U=r({name:"crudTable",components:{Table:e,Layer:u(x,[["render",function(e,l,a,t,o,n){const i=m("el-input"),r=m("el-form-item"),d=m("el-radio"),s=m("el-radio-group"),u=m("el-form"),V=m("Layer",!0);return c(),f(V,{layer:e.layer,onConfirm:e.submit,ref:"layerDom"},{default:p((()=>[h(u,{model:e.form,ref:"ruleForm","label-width":"230px",style:{"margin-right":"100px"}},{default:p((()=>[h(r,{label:"名称：",prop:"username"},{default:p((()=>[h(i,{modelValue:e.form.username,"onUpdate:modelValue":l[0]||(l[0]=l=>e.form.username=l),placeholder:"请输入名称"},null,8,["modelValue"])])),_:1}),h(r,{label:"Token：",prop:"token"},{default:p((()=>[h(i,{modelValue:e.form.token,"onUpdate:modelValue":l[1]||(l[1]=l=>e.form.token=l),placeholder:"请输入Token"},null,8,["modelValue"])])),_:1}),h(r,{label:"邀请好友发言限制：",prop:"inviteFriendsSet"},{default:p((()=>[h(s,{modelValue:e.form.config.inviteFriendsSet,"onUpdate:modelValue":l[2]||(l[2]=l=>e.form.config.inviteFriendsSet=l)},{default:p((()=>[(c(!0),g(b,null,v(e.radioData,(e=>(c(),f(d,{key:e.value,label:e.value},{default:p((()=>[y(_(e.label),1)])),_:2},1032,["label"])))),128))])),_:1},8,["modelValue"]),h(r,{label:"指定人数",prop:"inviteFriendsQuantity"},{default:p((()=>[h(i,{modelValue:e.form.config.inviteFriendsQuantity,"onUpdate:modelValue":l[3]||(l[3]=l=>e.form.config.inviteFriendsQuantity=l)},null,8,["modelValue"])])),_:1}),h(r,{label:"重置天数",prop:"inviteFriendsAutoClearTime"},{default:p((()=>[h(i,{modelValue:e.form.config.inviteFriendsAutoClearTime,"onUpdate:modelValue":l[4]||(l[4]=l=>e.form.config.inviteFriendsAutoClearTime=l)},null,8,["modelValue"])])),_:1})])),_:1}),h(r,{label:"关注频道发言限制：",prop:"followChannelSet"},{default:p((()=>[h(s,{modelValue:e.form.config.followChannelSet,"onUpdate:modelValue":l[5]||(l[5]=l=>e.form.config.followChannelSet=l)},{default:p((()=>[(c(!0),g(b,null,v(e.radioData,(e=>(c(),f(d,{key:e.value,label:e.value},{default:p((()=>[y(_(e.label),1)])),_:2},1032,["label"])))),128))])),_:1},8,["modelValue"])])),_:1}),h(r,{label:"系统消息删除：",prop:"deleteSeconds"},{default:p((()=>[h(i,{modelValue:e.form.config.deleteSeconds,"onUpdate:modelValue":l[6]||(l[6]=l=>e.form.config.deleteSeconds=l),placeholder:"请输入秒数"},null,8,["modelValue"])])),_:1}),h(r,{label:"邀请奖金功能：",prop:"invitationBonusSet"},{default:p((()=>[h(s,{modelValue:e.form.config.invitationBonusSet,"onUpdate:modelValue":l[7]||(l[7]=l=>e.form.config.invitationBonusSet=l)},{default:p((()=>[(c(!0),g(b,null,v(e.radioData,(e=>(c(),f(d,{key:e.value,label:e.value},{default:p((()=>[y(_(e.label),1)])),_:2},1032,["label"])))),128))])),_:1},8,["modelValue"]),h(r,{label:"指定人数：",prop:"inviteMembers"},{default:p((()=>[h(i,{modelValue:e.form.config.inviteMembers,"onUpdate:modelValue":l[8]||(l[8]=l=>e.form.config.inviteMembers=l)},null,8,["modelValue"])])),_:1}),h(r,{label:"每次获得奖金：",prop:"inviteEarnedOutstand"},{default:p((()=>[h(i,{modelValue:e.form.config.inviteEarnedOutstand,"onUpdate:modelValue":l[9]||(l[9]=l=>e.form.config.inviteEarnedOutstand=l)},null,8,["modelValue"])])),_:1}),h(r,{label:"联系人：",prop:"contactPerson"},{default:p((()=>[h(i,{modelValue:e.form.config.contactPerson,"onUpdate:modelValue":l[10]||(l[10]=l=>e.form.config.contactPerson=l)},null,8,["modelValue"])])),_:1})])),_:1})])),_:1},8,["model"])])),_:1},8,["layer","onConfirm"])}]])},setup(){const e=V({show:!1,title:"新增",showButton:!0}),l=V({index:1,size:20,total:0}),a=s(!0),r=s([]),d=s([]),u=e=>{a.value=!0,e&&(l.index=1);let o={page:l.index-1,pageSize:l.size};t(o).then((e=>{r.value=e.data.list,l.total=Number(e.data.pager.total)})).catch((e=>{r.value=[],l.index=1,l.total=0})).finally((()=>{a.value=!1}))};return u(!0),{tableData:r,chooseData:d,loading:a,page:l,layer:e,handleSelectionChange:e=>{d.value=e},handleAdd:()=>{e.title="新增数据",e.show=!0,delete e.row},handleEdit:l=>{e.title="编辑数据",e.row=l,e.show=!0},handleDel:e=>{let l={ids:e.map((e=>e.id)).join(",")};o(l).then((e=>{k({type:"success",message:e.msg}),u(1===r.value.length)}))},handleStart:e=>{n(e).then((e=>{console.log(e),u(!0)}))},handleStop:e=>{i(e).then((e=>{console.log(e),location.reload()})).catch((e=>{console.log(e)}))},getTableData:u}}}),A={class:"layout-container"},B={class:"layout-container-form flex space-between"},O={class:"layout-container-form-handle"},j={class:"layout-container-table"};var E=u(U,[["render",function(e,l,a,t,o,n){const i=m("el-button"),r=m("el-popconfirm"),d=m("el-table-column"),s=m("Table"),u=m("Layer"),b=S("loading");return c(),g("div",A,[C("div",B,[C("div",O,[h(i,{type:"primary",icon:"el-icon-circle-plus-outline",onClick:e.handleAdd},{default:p((()=>[y("新增")])),_:1},8,["onClick"]),h(r,{title:"批量删除",onConfirm:l[0]||(l[0]=l=>e.handleDel(e.chooseData))},{reference:p((()=>[h(i,{type:"danger",icon:"el-icon-delete",disabled:0===e.chooseData.length},{default:p((()=>[y("批量删除")])),_:1},8,["disabled"])])),_:1})])]),C("div",j,[D((c(),f(s,{ref:"table",page:e.page,"onUpdate:page":l[1]||(l[1]=l=>e.page=l),showIndex:!0,showSelection:!0,data:e.tableData,onGetTableData:e.getTableData,onSelectionChange:e.handleSelectionChange},{default:p((()=>[h(d,{prop:"username",label:"名称",align:"center"}),h(d,{prop:"token",label:"Token",align:"center"}),h(d,{prop:"state",label:"狀態",align:"center"}),h(d,{label:"操作",align:"center",fixed:"right",width:"275"},{default:p((l=>[h(i,{type:"success",onClick:a=>e.handleStart(l.row)},{default:p((()=>[y("启动")])),_:2},1032,["onClick"]),h(i,{type:"info",onClick:a=>e.handleStop(l.row)},{default:p((()=>[y("停止")])),_:2},1032,["onClick"]),h(i,{type:"primary",onClick:a=>e.handleEdit(l.row)},{default:p((()=>[y("编辑")])),_:2},1032,["onClick"]),h(r,{title:"删除",onConfirm:a=>e.handleDel([l.row])},{reference:p((()=>[h(i,{type:"danger"},{default:p((()=>[y("删除")])),_:1})])),_:2},1032,["onConfirm"])])),_:1})])),_:1},8,["page","data","onGetTableData","onSelectionChange"])),[[b,e.loading]]),e.layer.show?(c(),f(u,{key:0,layer:e.layer,onGetTableData:e.getTableData},null,8,["layer","onGetTableData"])):w("",!0)])])}]]);export{E as default};
