<?php

namespace App\Form;

use App\Entity\Product;
//use App\Entity\Category;
use Symfony\Component\Form\Extension\Core\Type\TextType ;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\FormTypeInterface;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\OptionsResolver\OptionsResolver;

class FormProductType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('Product_Name', TextType::class, [
                'label' => 'Product_Name',
                'attr' => [
                    'placeholder' => 'Enter product name',
                ],
                'required'=> false,
            ])
            ->add('Product_Quantity', TextType::class, [
                'label' => 'Product_Quantity',
                'attr' => [
                    'placeholder' => 'Enter product quantity',
                ],
                'required'=> false,

            ])
            ->add('Product_Label', TextType::class, [
                'label' => 'Product_Label',
                'attr' => [
                    'placeholder' => 'Enter product label',
                ],
                'required'=> false,

            ])
            ->add('Expiration_Date')
            ->add('Category_Key')
            ->add('save',SubmitType::class) 
            ->getForm()
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Product::class,
        ]);
    }
}
