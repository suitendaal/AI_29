function [] = data2PaNa(Total,Util,n, name)
%Plot distance to Pareto and Nash of a given domain
%% Distance to Nash
TC1=[];TB1=[];TH1=[];
for k=1:length(Util)
    d=Total;
    switch d(k,1)
        case "TimeDependentAgentConceder"
            if d(k,2)=="Group29_BoaParty"
                TC1=[TC1 Total(k,5)];       
            end
        case "TimeDependentAgentBoulware"
            if d(k,2)=="Group29_BoaParty"
                TB1=[TB1 Total(k,5)];       
            end
        case "TimeDependentAgentHardliner"
            if d(k,2)=="Group29_BoaParty"
                TH1=[TH1 Total(k,5)];
            end
        otherwise 
    end
end
%% 
TC1=double(strrep(TC1,",","."));
TB1=double(strrep(TB1,",","."));
TH1=double(strrep(TH1,",","."));
%%

figure; hold on;
subplot(3,1,1); hold on;
histogram(TC1,n,'BinWidth',0.05,'BinLimits',[0,1]); title("Conceder vs AI29"); xlabel("Own Utility");ylabel("Number of occurances");
subplot(3,1,2);hold on;
histogram(TB1,n,'BinWidth',0.05,'BinLimits',[0,1]); title("Boulware vs AI29");xlabel("Own Utility");ylabel("Number of occurances");
subplot(3,1,3);hold on;
histogram(TH1,n,'BinWidth',0.05,'BinLimits',[0,1]);title("Hardliner vs AI29 "); xlabel("Own Utility");ylabel("Number of occurances");
sgtitle(name+" Distance to Pareto");
%% Repeat for distance to Pareto 
TC2=[];TB2=[];TH2=[];
for k=1:length(Util)
    d=Total;
    switch d(k,1)
        case "TimeDependentAgentConceder"
            if d(k,2)=="Group29_BoaParty"
                TC2=[TC2 Total(k,6)];       
            end
        case "TimeDependentAgentBoulware"
            if d(k,2)=="Group29_BoaParty"
                TB2=[TB2 Total(k,6)];       
            end
        case "TimeDependentAgentHardliner"
            if d(k,2)=="Group29_BoaParty"
                TH2=[TH2 Total(k,6)];
            end
        otherwise 
    end
end
%% 
TC2=double(strrep(TC2,",","."));
TB2=double(strrep(TB2,",","."));
TH2=double(strrep(TH2,",","."));
%%
figure; hold on;
subplot(3,1,1); hold on;
histogram(TC2,n,'BinWidth',0.05,'BinLimits',[0,1]); title("Conceder vs AI29"); xlabel("Own Utility");ylabel("Number of occurances");
subplot(3,1,2);hold on;
histogram(TB2,n,'BinWidth',0.05,'BinLimits',[0,1]); title("Boulware vs AI29");xlabel("Own Utility");ylabel("Number of occurances");
subplot(3,1,3);hold on;
histogram(TH2,n,'BinWidth',0.05,'BinLimits',[0,1]);title("Hardliner vs AI29 "); xlabel("Own Utility");ylabel("Number of occurances");
sgtitle(name+" Distance to Nash");
end

