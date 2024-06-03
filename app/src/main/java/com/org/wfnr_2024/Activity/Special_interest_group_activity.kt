package com.org.wfnr_2024.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.R
import org.impactindiafoundation.wfnr.Adapter.Special_Interest_Group_Adapter
import org.impactindiafoundation.wfnr.CallBack.SIGNext
import org.impactindiafoundation.wfnr.CallBack.SendMailCallBack
import org.impactindiafoundation.wfnr.Model.Email
import org.impactindiafoundation.wfnr.Model.Special_Interest_Group_Model



class Special_interest_group_activity:AppCompatActivity(), SIGNext,SendMailCallBack,
    View.OnClickListener {

    var Special_Interest_Group_ArrayList:ArrayList<Special_Interest_Group_Model>?=null

    var AquaticTherapyEmailArrayList:ArrayList<Email>?=null
    var AlternativeMedicineEmailArrayList:ArrayList<Email>?=null
    var AutonomicNeurorehabilitationArrayList:ArrayList<Email>?=null
    var Botulinum_Toxin:ArrayList<Email>?=null
    var BrainComputerInterfaces:ArrayList<Email>?=null
    var Brain_Modulation_for_Neurorehabilitation:ArrayList<Email>?=null
    var Clinical_Applications_of_Advanced_Technologies:ArrayList<Email>?=null
    var Clinical_Pathways:ArrayList<Email>?=null
    var Cognitive_Neurorehabilitation:ArrayList<Email>?=null
    var Communication_Disorders:ArrayList<Email>?=null
    var Community_Based_Neurorehabilitation:ArrayList<Email>?=null
    var Continence_and_Bowel_Management_in_Neurorehabilitation:ArrayList<Email>?=null
    var Dementia:ArrayList<Email>?=null
    var Developing_World_Forum:ArrayList<Email>?=null
    var Dysphagia:ArrayList<Email>?=null
    var Early_Neurorehabilitation:ArrayList<Email>?=null
    var Headache_and_Migraine:ArrayList<Email>?=null
    var Health_Policy_and_Economics_in_Neurorehabilitation:ArrayList<Email>?=null
    var MAC_Measurement_Assessment_and_Classification:ArrayList<Email>?=null
    var Mild_and_Severe_Brain_Injury:ArrayList<Email>?=null
    var Motivation_in_Neurorehabilitation:ArrayList<Email>?=null
    var Multiple_Sclerosis_and_Demyelinating_Disorders:ArrayList<Email>?=null
    var Neurological_Conditions_and_Driving:ArrayList<Email>?=null
    var Neurologic_Music_Therapy:ArrayList<Email>?=null
    var NeuroPalliative_Care:ArrayList<Email>?=null
    var Neuropathic_Pain:ArrayList<Email>?=null
    var Neuropharmacology:ArrayList<Email>?=null
    var Neurophilosophy_and_Ethics:ArrayList<Email>?=null
    var Neurophysiology_and_Neurorehabilitation:ArrayList<Email>?=null
    var Neuropsychological_Rehabilitation:ArrayList<Email>?=null
    var Neurorehabilitation_Service_Development:ArrayList<Email>?=null
    var Neurosurgical_Reconstructive_and_Restorative_Rehabilitation:ArrayList<Email>?=null
    var Organisation_for_Psychological_Research_into_Stroke:ArrayList<Email>?=null
    var Paediatric_Neurorehabilitation:ArrayList<Email>?=null
    var Plexopathies:ArrayList<Email>?=null
    var Posture_Mobility_and_Falls:ArrayList<Email>?=null
    var Rehabilitation_for_Movement_Disorders:ArrayList<Email>?=null
    var Robotics:ArrayList<Email>?=null
    var Spinal_Cord_Injury:ArrayList<Email>?=null
    var Stroke_Rehabilitation:ArrayList<Email>?=null
    var Telerehabilitation:ArrayList<Email>?=null
    var Women_in_Neurorehabilitation:ArrayList<Email>?=null
    var Yoga_Therapy_in_Neurorehabilitation:ArrayList<Email>?=null
    var Young_WFNR:ArrayList<Email>?=null

    lateinit var imageView_back:ImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sig_)

        imageView_back=findViewById(R.id.imageView_back)

        imageView_back.setOnClickListener(this)

        Special_Interest_Group_ArrayList= ArrayList()

        AquaticTherapyEmailArrayList= ArrayList()
        AlternativeMedicineEmailArrayList= ArrayList()
        AutonomicNeurorehabilitationArrayList= ArrayList()
        Botulinum_Toxin=ArrayList()
        BrainComputerInterfaces= ArrayList()
        Brain_Modulation_for_Neurorehabilitation= ArrayList()
        Clinical_Applications_of_Advanced_Technologies= ArrayList()
        Clinical_Pathways= ArrayList()
        Cognitive_Neurorehabilitation= ArrayList()
        Communication_Disorders= ArrayList()
        Community_Based_Neurorehabilitation= ArrayList()
        Continence_and_Bowel_Management_in_Neurorehabilitation= ArrayList()
        Dementia= ArrayList()
        Developing_World_Forum= ArrayList()
        Dysphagia= ArrayList()
        Early_Neurorehabilitation= ArrayList()
        Headache_and_Migraine= ArrayList()
        Health_Policy_and_Economics_in_Neurorehabilitation= ArrayList()
        MAC_Measurement_Assessment_and_Classification= ArrayList()
        Mild_and_Severe_Brain_Injury= ArrayList()
        Motivation_in_Neurorehabilitation= ArrayList()
        Multiple_Sclerosis_and_Demyelinating_Disorders= ArrayList()
        Neurological_Conditions_and_Driving= ArrayList()
        Neurologic_Music_Therapy= ArrayList()
        NeuroPalliative_Care= ArrayList()
        Neuropathic_Pain= ArrayList()
        Neuropharmacology= ArrayList()
        Neurophilosophy_and_Ethics= ArrayList()
        Neurophysiology_and_Neurorehabilitation= ArrayList()
        Neuropsychological_Rehabilitation= ArrayList()
        Neurorehabilitation_Service_Development= ArrayList()
        Neurosurgical_Reconstructive_and_Restorative_Rehabilitation= ArrayList()
        Organisation_for_Psychological_Research_into_Stroke= ArrayList()
        Paediatric_Neurorehabilitation= ArrayList()
        Plexopathies= ArrayList()
        Posture_Mobility_and_Falls= ArrayList()
        Rehabilitation_for_Movement_Disorders= ArrayList()
        Robotics= ArrayList()
        Spinal_Cord_Injury= ArrayList()
        Stroke_Rehabilitation= ArrayList()
        Telerehabilitation= ArrayList()
        Women_in_Neurorehabilitation= ArrayList()
        Yoga_Therapy_in_Neurorehabilitation= ArrayList()
        Young_WFNR= ArrayList()


        AquaticTherapyEmailArrayList!!.add(Email("Brinda Merchant","merchantbrinda@gmail.com"))

        AlternativeMedicineEmailArrayList!!.add(Email("Tiebin YAN","Dr.yan@126.com"))
        AutonomicNeurorehabilitationArrayList!!.add(Email("Ellen Merete Hagen","e-mhagen@online.no"))

        Botulinum_Toxin!!.add(Email("Gerard Francisco","Gerard.E.Francisco@uth.tmc.edu"))
        Botulinum_Toxin!!.add(Email("Jorge Hernandez Franco","jhfranco@medicapolanco.com"))
        BrainComputerInterfaces!!.add(Email("Surjo R Soekadar","surjo.soekadar@charite.de"))

        Brain_Modulation_for_Neurorehabilitation!!.add(Email("Chair\n" +
                "Nam-Jong Paik\n" +
                "Seoul National University\n" +
                "South Korea","njpaik@snu.ac.kr"))
        Brain_Modulation_for_Neurorehabilitation!!.add(Email("Secretary\n" +
                "Pratik Chahhatbar MD PhD","Pratik.chhatbar@duke.edu"))

        Clinical_Applications_of_Advanced_Technologies!!.add(Email("Tamsin Reed","Tamsin.reed@hcahealthcare.co.uk"))
        Clinical_Pathways!!.add(Email("Thomas Platz","t.platz@bdh-klinik-greifswald.de"))
        Clinical_Pathways!!.add(Email("Mayowa Owolabi","mayowaowolabi@yahoo.com"))

        Cognitive_Neurorehabilitation!!.add(Email("Prof. Stephanie Clarke\n" +
                "Service de Neuropsychologie et de Neuroréhabilitation\n" +
                "CHUV, Lausanne, Switzerland","Stephanie.Clarke@chuv.ch"))
        Cognitive_Neurorehabilitation!!.add(Email("Prof. Gilles Rode\n" +
                "Université de Lyon\n" +
                "INSERM-UMRS 534, Bron, France","gilles.rode@chu-lyon.fr"))

        Communication_Disorders!!.add(Email("Apoorva Pauranik","apauranik@gmail.com"))

        Community_Based_Neurorehabilitation!!.add(Email("Alessandro Giustini","alessandro.giustini@ntc.it"))

        Continence_and_Bowel_Management_in_Neurorehabilitation!!.add(Email("Yousif Al Nuaimi","yalneaimi@hotmail.com"))

        Dementia!!.add(Email("Professor Arseny Sokolov","arseny.sokolov@chuv.ch"))
        Dementia!!.add(Email("Valeria Manera","valeria.manera@univ-cotedazur.fr"))
        Dementia!!.add(Email("Sara Isernia","sisernia@dongnocchi.it"))

        Developing_World_Forum!!.add(Email("Nirmal Surya","nirmal_surya@yahoo.com"))

        Dysphagia!!.add(Email("Irene Battel","irene.battel@gmail.com"))

        Early_Neurorehabilitation!!.add(Email("Karin Diserens","Karin.Diserens@chuv.ch"))
        Early_Neurorehabilitation!!.add(Email("Peter Lackner","peter.lackner@gesundheitsverbund.at"))

        Headache_and_Migraine!!.add(Email("Tissa Wijeratne","twijeratne@gmail.com"))
        Health_Policy_and_Economics_in_Neurorehabilitation!!.add(Email("Stefan Strilciuc","stefan.strilciuc@brainscience.ro"))
        MAC_Measurement_Assessment_and_Classification!!.add(Email("Matilde Leonardi","Matilde.Leonardi@istituto-besta.it"))
        MAC_Measurement_Assessment_and_Classification!!.add(Email("Klemens Fheodoroff","klemens.fheodoroff@me.com"))

        Mild_and_Severe_Brain_Injury!!.add(Email("Caterina Pistarini","caterina.pistarini@icsmaugeri.it"))

        Motivation_in_Neurorehabilitation!!.add(Email("Dana Boering","danaboering@gmail.com"))
        Multiple_Sclerosis_and_Demyelinating_Disorders!!.add(Email("Abhishek Srivastava","29abhi@gmail.com"))
        Multiple_Sclerosis_and_Demyelinating_Disorders!!.add(Email("Juerg Kesselring","juerg.kesselring@kliniken-valens.ch"))

        Neurological_Conditions_and_Driving!!.add(Email("Carol Hawley","C.A.Hawley@warwick.ac.uk"))
        Neurologic_Music_Therapy!!.add(Email("Chair\n" +
                "Michael Thaut","michael.thaut@utoronto.ca"))

        NeuroPalliative_Care!!.add(Email("Dr Navita Purohit","drnavita.nv@gmail.com"))

        Neuropathic_Pain!!.add(Email("Marta Imamura","marta.imamura@fm.usp.br"))
        Neuropharmacology!!.add(Email("Dafin Muresanu","dafinm@ssnn.ro"))

        Neurophilosophy_and_Ethics!!.add(Email("Dr Heinrich Binder","heinrich.dr.binder@outlook.com"))
        Neurophilosophy_and_Ethics!!.add(Email("Dr Giorgio Sandrini","giorgio.sandrini@unipv.it"))
        Neurophilosophy_and_Ethics!!.add(Email("Sabahat Asim Wasti","WastiS@ClevelandClinicAbuDhabi.ae"))

        Neurophysiology_and_Neurorehabilitation!!.add(Email("Murat Zinnuroğlu","muratz@gmail.com"))

        Neuropsychological_Rehabilitation!!.add(Email("Robyn Tate","robyn.tate@sydney.edu.au"))
        Neurorehabilitation_Service_Development!!.add(Email("Sabahat Asim Wasti","WastiS@ClevelandClinicAbuDhabi.ae"))

        Neurosurgical_Reconstructive_and_Restorative_Rehabilitation!!.add(Email("Maximilian Mehdorn","hmmehdorn@me.com"))
        Organisation_for_Psychological_Research_into_Stroke!!.add(Email("Stephanie Rossit","S.Rossit@uea.ac.uk"))

        Paediatric_Neurorehabilitation!!.add(Email("Andrea Martinuzzi","andrea.martinuzzi@lanostrafamiglia.it"))
        Paediatric_Neurorehabilitation!!.add(Email("Professor El Oumri","aa.eloumri@gmail.com"))
        Plexopathies!!.add(Email("Jan Groothuis","Jan.Groothuis@radboudumc.nl"))
        Posture_Mobility_and_Falls!!.add(Email("Dominic Perennou","DPerennou@chu-grenoble.fr"))
        Posture_Mobility_and_Falls!!.add(Email("Klaus Martin Stephan","klausmartinstephan@web.de\n" +
                "\n"))
        Rehabilitation_for_Movement_Disorders!!.add(Email("Rajinder Dhamija","dhamijark@gov.in"))

        Robotics!!.add(Email("Hermano Igo Krebs","hikrebs@mit.edu\n" +
                "\n"))
        Robotics!!.add(Email("Dylan Edwards","dylan.edwards@jefferson.edu"))

        Spinal_Cord_Injury!!.add(Email("Humberto Cerrel Bazo","hcb57@yahoo.com"))

        Stroke_Rehabilitation!!.add(Email("Wayne Feng","wayne.feng@duke.edu"))

        Telerehabilitation!!.add(Email("Annie Hill","A.Hill2@latrobe.edu.au"))
        Telerehabilitation!!.add(Email("Carl Froilan Leochico","cfdleochico@stlukes.com.ph"))

        Women_in_Neurorehabilitation!!.add(Email("Sangeetha Madhavan","smadhava@uic.edu"))
        Women_in_Neurorehabilitation!!.add(Email("Jyutika Mehta","jmehta@twu.edu"))

        Yoga_Therapy_in_Neurorehabilitation!!.add(Email("Pazhani Ranganathan","pazran@hotmail.com"))

        Young_WFNR!!.add(Email("Maria Alejandra Spir Brunal","maria.spir@udea.edu.co"))


        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Aquatic Therapy",getString(
            R.string.Aquatic_Therapy
        ),AquaticTherapyEmailArrayList))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Alternative Medicine",getString(
            R.string.Alternative_Medicine
        ),AlternativeMedicineEmailArrayList))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Autonomic Neurorehabilitation (ANSIG)",getString(
            R.string.Autonomic_Neurorehabilitation
        ),AutonomicNeurorehabilitationArrayList))


        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Botulinum Toxin",getString(R.string.Botulinum_Toxin),Botulinum_Toxin))

        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Brain-Computer Interfaces",getString(R.string.Brain_Computer_Interfaces),BrainComputerInterfaces))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Brain Modulation for Neurorehabilitation",getString(R.string.Brain_Modulation_for_Neurorehabilitation),Brain_Modulation_for_Neurorehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Clinical Applications of Advanced Technologies",getString(R.string.Clinical_Applications_of_Advanced_Technologies),Clinical_Applications_of_Advanced_Technologies))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Clinical Pathways",getString(R.string.Clinical_Pathways),Clinical_Pathways))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Cognitive Neurorehabilitation",getString(R.string.Cognitive_Neurorehabilitation),Cognitive_Neurorehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Communication Disorders",getString(R.string.Communication_Disorders),Communication_Disorders))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Community Based Neurorehabilitation",getString(R.string.Community_Based_Neurorehabilitation),Community_Based_Neurorehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Continence and Bowel Management in Neurorehabilitation","",Continence_and_Bowel_Management_in_Neurorehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Dementia",getString(R.string.Dementia),Dementia))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Developing World Forum",getString(R.string.Developing_World_Forum),Developing_World_Forum))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Dysphagia",getString(R.string.Dysphagia),Dysphagia))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Early Neurorehabilitation",getString(R.string.Early_Neurorehabilitation),Early_Neurorehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Headache and Migraine","",Headache_and_Migraine))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Health Policy and Economics in Neurorehabilitation",getString(R.string.Health_Policy_and_Economics_in_Neurorehabilitation),Health_Policy_and_Economics_in_Neurorehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("MAC: Measurement, Assessment and Classification",getString(R.string.MAC_Measurement_Assessment_and_Classification),MAC_Measurement_Assessment_and_Classification))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Mild and Severe Brain Injury",getString(R.string.Mild_and_Severe_Brain_Injury),Mild_and_Severe_Brain_Injury))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Motivation in Neurorehabilitation",getString(R.string.Motivation_in_Neurorehabilitation),Motivation_in_Neurorehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Multiple Sclerosis and Demyelinating Disorders",getString(R.string.Multiple_Sclerosis_and_Demyelinating_Disorders),Multiple_Sclerosis_and_Demyelinating_Disorders))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Neurological Conditions and Driving",getString(R.string.Neurological_Conditions_and_Driving),Neurological_Conditions_and_Driving))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Neurologic Music Therapy",getString(R.string.Neurologic_Music_Therapy),Neurologic_Music_Therapy))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("NeuroPalliative Care","",NeuroPalliative_Care))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Neuropathic Pain","",Neuropathic_Pain))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Neuropharmacology","",Neuropharmacology))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Neurophilosophy and Ethics",getString(R.string.Neurophilosophy_and_Ethics),Neurophilosophy_and_Ethics))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Neurophysiology and Neurorehabilitation",getString(R.string.Neurophysiology_and_Neurorehabilitation),Neurophysiology_and_Neurorehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Neuropsychological Rehabilitation",getString(R.string.Neuropsychological_Rehabilitation),Neuropsychological_Rehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Neurorehabilitation Service Development",getString(R.string.Neurorehabilitation_Service_Development),Neurorehabilitation_Service_Development))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Neurosurgical, Reconstructive and Restorative Rehabilitation",getString(R.string.Neurosurgical_Reconstructive_and_Restorative_Rehabilitation),Neurosurgical_Reconstructive_and_Restorative_Rehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Organisation for Psychological Research into Stroke (OPSYRIS)",getString(R.string.Organisation_for_Psychological_Research_into_Stroke),Organisation_for_Psychological_Research_into_Stroke))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Paediatric Neurorehabilitation",getString(R.string.Paediatric_Neurorehabilitation),Paediatric_Neurorehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Plexopathies",getString(R.string.Plexopathies),Plexopathies))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Posture, Mobility and Falls","",Posture_Mobility_and_Falls))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Rehabilitation for Movement Disorders",getString(R.string.Rehabilitation_for_Movement_Disorders),Rehabilitation_for_Movement_Disorders))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Robotics",getString(R.string.Robotics),Robotics))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Spinal Cord Injury",getString(R.string.Spinal_Cord_Injury),Spinal_Cord_Injury))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Stroke Rehabilitation","",Stroke_Rehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Telerehabilitation",getString(R.string.Telerehabilitation),Telerehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Women in Neurorehabilitation",getString(R.string.Women_in_Neurorehabilitation),Women_in_Neurorehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Yoga Therapy in Neurorehabilitation",getString(R.string.Yoga_Therapy_in_Neurorehabilitation),Yoga_Therapy_in_Neurorehabilitation))
        Special_Interest_Group_ArrayList!!.add(Special_Interest_Group_Model("Young WFNR",getString(R.string.Young_WFNR),Young_WFNR))

        val recyclerView1: RecyclerView = findViewById(R.id.RecyclerView_special_interest_groups)
        val adapter1 = Special_Interest_Group_Adapter(this, Special_Interest_Group_ArrayList!!,this,this)
        val layoutManager1 = LinearLayoutManager(this)
        layoutManager1.orientation = LinearLayoutManager.VERTICAL // or HORIZONTAL
        recyclerView1.layoutManager = layoutManager1

        recyclerView1.adapter = adapter1
    }

    override fun OnNextPage(data: Special_Interest_Group_Model, position: Int, view: View) {
        // Create a Bundle
        val bundle = Bundle()

        // Put data into the Bundle
        bundle.putParcelable("SPECIAL_INTEREST_GROUP_DATA", data)

        // Create an Intent to start the receiving activity
        val intent = Intent(this, SIG_Description_Activity::class.java)

        // Attach the Bundle to the Intent
        intent.putExtras(bundle)

        // Start the new activity
        startActivity(intent)
    }

    override fun sendMail(data: Email, position: Int, view: View,topic:String) {
        Log.d("mytag","sendMail clicked=>"+data)
        Log.d("mytag","sendMail topic clicked=>"+topic)


       // var email_CC="digitalhealth@cmplin.com"
       // var email_BCC="devcmpl@gmail.com"
        var email_TO=data.email
        var email_From=""
        var subject=topic+" "+"SIG of WFNR"

        //var message="Dear Admin, You have query from the IAN App,\n"+subject+"\n"+binding.editTextContactMessage.text.toString()+"\n"+"regards \n IAN Digital Team"

        // define Intent object with action attribute as ACTION_SEND

        // define Intent object with action attribute as ACTION_SEND
        val intent = Intent(Intent.ACTION_SEND)

        // add three fields to intent using putExtra function

        // add three fields to intent using putExtra function
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email_TO))
       // intent.putExtra(Intent.EXTRA_CC, arrayOf(email_CC))
        //intent.putExtra(Intent.EXTRA_BCC, arrayOf(email_BCC))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //intent.putExtra(Intent.EXTRA_TEXT, message)

        // set type of intent

        // set type of intent
        intent.type = "message/rfc822"

        // startActivity with intent with chooser as Email client using createChooser function

        // startActivity with intent with chooser as Email client using createChooser function
        startActivity(Intent.createChooser(intent, "Choose an Email client :"))
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.imageView_back->
            {
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}