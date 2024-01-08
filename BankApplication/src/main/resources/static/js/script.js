const toggleSideBar=()=>{
	
	if($(".sidebar").is(":visible")){
		
		$(".sidebar").css("display","none");
		$(".content").css("left-margin","0%");
	}
	
	else{
		$(".sidebar").css("display","block");
		$(".content").css("left-margin","20%");
	}
	
	
};

const search=()=>{
	
	let query=$("#search-input").val();
	
	if(query==''){
		$(".search-result").hide();
	}
	
	else{
		
		let url=`http://localhost:7777/search/${query}`;
		
		fetch(url).then((response)=>{
			return response.json();
		})
		.then((data)=>{
			let text=`<div class='list-group'>`
			
			data.forEach((customers)=>{
				text+=`<a href='/licagent/${customers.cId}/customers' class='list-group-item list-group-item-action'>${customers.name} </a>`
				
				
			});
			
			text+=`</div>`;
			$(".search-result").html(text);
			$(".search-result").show();
			
		});
}
	
};