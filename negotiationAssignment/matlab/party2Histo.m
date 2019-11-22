function [] = party2Histo(TotalS11, Util11,n,f,x)
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
if f
figure; hold on;
subplot(2,3,1); 
h=histogram(TC1,n,'BinWidth',0.05,'BinLimits',[0,1]); title("Conceder vs Us");xlabel("Own Utility");ylabel("Number of occurances");
subplot(2,3,2);
h=histogram(TB1,n,'BinWidth',0.05,'BinLimits',[0,1]); title("Boulware vs Us");xlabel("Own Utility");ylabel("Number of occurances");
subplot(2,3,3);
h=histogram(TH1,n,'BinWidth',0.05,'BinLimits',[0,1]); title("Hardliner vs Us ");xlabel("Own Utility");ylabel("Number of occurances");
subplot(2,3,4);
h=histogram(TC2,n,'BinWidth',0.05,'BinLimits',[0,1]); title("Us vs Conceder ");xlabel("Own Utility");ylabel("Number of occurances");
subplot(2,3,5);
h=histogram(TB2,n,'BinWidth',0.05,'BinLimits',[0,1]); title("Us vs Boulware");xlabel("Own Utility");ylabel("Number of occurances");
subplot(2,3,6);
h=histogram(TH2,n,'BinWidth',0.05,'BinLimits',[0,1]); title("Us vs Hardliner"); xlabel("Own Utility");ylabel("Number of occurances");

sgtitle(x);
else
figure; hold on;
subplot(2,3,1);xlim([0 1]); 
histogram(TC1,n);xlim([0 1]); title("Conceder vs Us");xlabel("Own Utility");ylabel("Number of occurances");
subplot(2,3,2);
histogram(TB1,n);xlim([0 1]); title("Boulware vs Us");xlabel("Own Utility");ylabel("Number of occurances");
subplot(2,3,3);
histogram(TH1,n);xlim([0 1]); title("Hardliner vs Us ");xlabel("Own Utility");ylabel("Number of occurances");
subplot(2,3,4);
histogram(TC2,n);xlim([0 1]); title("Us vs Conceder ");xlabel("Own Utility");ylabel("Number of occurances");
subplot(2,3,5);
histogram(TB2,n);xlim([0 1]); title("AI29 vs Boulware");xlabel("Own Utility");ylabel("Number of occurances");
subplot(2,3,6);
histogram(TH2,n);xlim([0 1]); title("Us vs Hardliner");xlabel("Own Utility");ylabel("Number of occurances");
sgtitle(x);
end
end

