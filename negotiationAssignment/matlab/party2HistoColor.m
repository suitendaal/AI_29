function [] = party2HistoColor(TotalS11, Util11,n,f,x,filename)
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here
TC1=[];TB1=[];TH1=[];TC2=[];TB2=[];TH2=[];
for k=1:length(Util11)
    d=TotalS11;
    switch d(k,1)
        case "TimeDependentAgentConceder"
            if d(k,2)=="Group29_BoaParty"
                TC1=[TC1 Util11(k,2)];       
            end
        case "TimeDependentAgentBoulware"
            if d(k,2)=="Group29_BoaParty"
                TB1=[TB1 Util11(k,2)];       
            end
        case "TimeDependentAgentHardliner"
            if d(k,2)=="Group29_BoaParty"
                TH1=[TH1 Util11(k,2)];
            end
        otherwise 
%             disp("error")   
    end
    switch d(k,2)
        case "TimeDependentAgentConceder"
            if d(k,1)=="Group29_BoaParty"
                TC2=[TC2 Util11(k,1)];       
            end
        case "TimeDependentAgentBoulware"
            if d(k,1)=="Group29_BoaParty"
                TB2=[TB2 Util11(k,1)];       
            end
        case "TimeDependentAgentHardliner"
            if d(k,1)=="Group29_BoaParty"
                TH2=[TH2 Util11(k,1)]; 
            end
        otherwise 
%             disp("error")   
    end

end
%%
% filename=csvName;
m = readtable(filename,...
    'Delimiter',';','ReadVariableNames',true);
Util1=m.Utility1;
Util2=m.Utility2;
Util=[Util1, Util2];
for i=1:length(m.Agent1)  
    str=m.Agent1{i};
    Agent1(i) = convertCharsToStrings(extractBefore(str,"@"));
    str=m.Agent2{i};
    Agent2(i) = convertCharsToStrings(extractBefore(str,"@"));
end

Agents  = [Agent1; Agent2];
AgentsI = [Agent1; Agent2]';

Data    = [m.min_util_, m.max_util_, m.Dist_ToPareto, m.Dist_ToNash];
Total   = [AgentsI Data];
TotalS  = sort(Total,1); %sort on the first dimension


H1=[];H2=[];
parfor k=1:length(Util)
    d=Total;
    d(:,3:end)=strrep(d(:,3:end),",",".");
    if d(k,1)=="Group29_BoaParty"
        H1=[H1 Util(k,1)];       
    end
    if d(k,2)=="Group29_BoaParty"
        H2=[H2 Util(k,2)];
    end
end
%     figure;hold on; histogram(H1,100);
%     figure;hold on; histogram(H2,100);





%%
if f
    figure; hold on;
    subplot(2,2,1); hold on;
    histogram(TC1,n,'BinWidth',0.05,'BinLimits',[0,1]); xlabel("Own Utility");ylabel("Number of occurances");
    subplot(2,2,1);hold on;
    histogram(TB1,n,'BinWidth',0.05,'BinLimits',[0,1]); xlabel("Own Utility");ylabel("Number of occurances");
    subplot(2,2,1);hold on;
    histogram(TH1,n,'BinWidth',0.05,'BinLimits',[0,1]); xlabel("Own Utility");ylabel("Number of occurances");
    subplot(2,2,2);hold on;
    histogram(TC2,n,'BinWidth',0.05,'BinLimits',[0,1]); xlabel("Own Utility");ylabel("Number of occurances");
    subplot(2,2,2);hold on;
    histogram(TB2,n,'BinWidth',0.05,'BinLimits',[0,1]); xlabel("Own Utility");ylabel("Number of occurances");
    subplot(2,2,2);hold on;
    histogram(TH2,n,'BinWidth',0.05,'BinLimits',[0,1]); xlabel("Own Utility");ylabel("Number of occurances");
    subplot(2,2,3);hold on;
    histogram(H1,100);title("Own vs Own, starting");
    subplot(2,2,4);hold on;
    histogram(H2,100);title("Own vs Own, second");
    legend("a","b","c","d");
    sgtitle(x);
else
    figure; hold on;
    subplot(2,2,1);xlim([0 1]); hold on;
    histogram(TC1,n);xlim([0 1]);xlabel("Own Utility");ylabel("Number of occurances");
    subplot(2,2,1);hold on;
    histogram(TB1,n);xlim([0 1]);xlabel("Own Utility");ylabel("Number of occurances");
    subplot(2,2,1);hold on;
    histogram(TH1,n);xlim([0 1]);xlabel("Own Utility");ylabel("Number of occurances");
    subplot(2,2,2);hold on;
    histogram(TC2,n);xlim([0 1]);xlabel("Own Utility");ylabel("Number of occurances");
    subplot(2,2,2);hold on;
    histogram(TB2,n);xlim([0 1]);xlabel("Own Utility");ylabel("Number of occurances");
    subplot(2,2,2);hold on;
    histogram(TH2,n);xlim([0 1]);xlabel("Own Utility");ylabel("Number of occurances");

    subplot(2,2,3);hold on;
    histogram(H1,100);title("Own vs Own, starting");
    subplot(2,2,4);hold on;
    histogram(H2,100);title("Own vs Own, second");
    sgtitle(x);
end
end

